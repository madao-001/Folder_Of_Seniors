package ncu.folder_of_seniors.module.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.activity.ChatActivity;
import ncu.folder_of_seniors.module.ui.activity.ResourceDetailsActivity;
import ncu.folder_of_seniors.module.ui.activity.ReviewActivity;

public class MyBoughtAdapter extends RecyclerView.Adapter<MyBoughtAdapter.VH>implements View.OnClickListener {

    private List<UserAction> mDatas;
    private Context mContext;
    private UserAction data;
    private Resource resource;
    private AlertDialog alertDialog;
    private BmobIMUserInfo info;

    private MyBoughtAdapter.Callback mCallback;

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface Callback {

        public void click(View v);

    }


    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView title;
        public final TextView price;
        public final TextView time;
        public final Button download;
        public final Button review;
        public final TextView messeage;

        public VH(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.my_selled_bought_iv_pic);
            title = (TextView) view.findViewById(R.id.my_selled_bought_tv_title);
            price = (TextView) view.findViewById(R.id.my_selled_bought_tv_price);
            time = (TextView) view.findViewById(R.id.my_selled_bought_tv_time);
            download = (Button) view.findViewById(R.id.my_selled_bought_bt_download);
            review = (Button)view.findViewById(R.id.my_selled_bought_bt_review);
            messeage = (TextView) view.findViewById(R.id.my_selled_bought_tv_message);
        }
    }

    public MyBoughtAdapter(Context mContext, List<UserAction> data, MyBoughtAdapter.Callback callback) {
        this.mDatas = data;
        this.mContext = mContext;
        mCallback = callback;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(MyBoughtAdapter.VH holder, final int position) {
        //设置数据
        data = mDatas.get(position);
        resource=data.getResource();
        //构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
        info = new BmobIMUserInfo(resource.getCreator().getObjectId(), resource.getCreator().getUsername(), resource.getCreator().getIcon());
        if(resource!=null){
            if(resource.getPhotos()!=null&&resource.getPrice()!=null&&resource.getTitle()!=null){
                holder.title.setText(resource.getTitle());
                holder.price.setText("￥"+resource.getPrice());
                holder.time.setText("购买于："+data.getCreatedAt());
                Glide.with(mContext).load(resource.getPhotos().get(0)).into(holder.image);
                if(data.getGrade()!=null){
                    holder.review.setText("已评价");
                }
            }else {
                holder.title.setText("该商品已被删除，详情请联系卖家");
            }
        }else {
            holder.title.setText("该商品已被删除，详情请联系卖家");
        }

        holder.messeage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //在这里跳转到和卖家聊天页面
                BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", conversationEntrance);
                Intent intent = new Intent();
                intent.setClass(mContext, ChatActivity.class);
                intent.putExtra("chat", bundle);
                mContext.startActivity(intent);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource resource = mDatas.get(position).getResource();
                //Toast.makeText(mContext,"name is "+sell.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ResourceDetailsActivity.class);
                intent.putExtra("objectId", resource.getObjectId());
                mContext.startActivity(intent);
            }
        });

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                BmobFile bmobFile = mDatas.get(position).getResource().getFile();
                File saveFile = new File(Environment.getExternalStorageDirectory(), bmobFile.getFilename());
                bmobFile.download(saveFile, new DownloadFileListener() {
                    @Override
                    public void done(String savePath, BmobException e) {
                        if(e==null){
                            dismissLoadingDialog();
                            Toast.makeText(mContext,"下载成功,保存路径:"+savePath,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext,"下载失败："+e.getErrorCode()+","+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer value, long total) {

                    }
                });
            }
        });

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.getGrade()==null){
                    Intent i = new Intent(mContext, ReviewActivity.class);
                    i.putExtra("resourceId",mDatas.get(position).getObjectId());
                    mContext.startActivity(i);
                }else
                    Toast.makeText(mContext,"已经评价过该资源！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public MyBoughtAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_selled_bought_item, parent, false);
        return new MyBoughtAdapter.VH(v);
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View view) {
        mCallback.click(view);
    }

    public void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
