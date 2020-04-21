package ncu.folder_of_seniors.module.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.activity.SellEditActivity;

public class MyLaunchAdapter extends RecyclerView.Adapter<MyLaunchAdapter.RE> implements View.OnLongClickListener{
    private List<Resource> sellList=new ArrayList<Resource>();
    private Context context;
    private Resource resource;
    private Button bt_deprice;
    private Button bt_update;
    private Integer likeNo=0;
    private Integer starNo=0;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public MyLaunchAdapter(Context context, List<Resource> sellList) {
        this.context=context;
        this.sellList=sellList;
    }

    @Override
    public RE onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_resource_item, parent, false);
        return new MyLaunchAdapter.RE(v);
    }

    @Override
    public void onBindViewHolder(final RE holder, final int position) {
        //赋值操作
        resource=sellList.get(position);
        if (resource.getPhotos().get(0)!=null){
            Glide.with(context).load(resource.getPhotos().get(0)).into(holder.re_im_head);
        }
        if (resource.getTitle()!=null){
            ((RE)holder).re_tv_title.setText(resource.getTitle());
        }
        ((RE)holder).re_tv_jifen.setText(resource.getPrice()+"积分");
        getLikeStarNo(resource, new FouthFLisentener() {
            @Override
            public void onSeccess(int num1, int num2, int num3) {
                (holder).re_tv_like.setText("超赞： "+num1);
                (holder).re_tv_star.setText("收藏： "+num2);
            }

            @Override
            public void onFails(String msg) {
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

        holder.bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SellEditActivity.class);
                intent.putExtra("objectId",sellList.get(position).getObjectId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return sellList.size();
    }

    @Override
    public boolean onLongClick(View v) {

        return false;
    }


    public class RE extends RecyclerView.ViewHolder {
        public ImageView re_im_head;
        public TextView re_tv_title;
        public TextView re_tv_jifen;
        public TextView re_tv_like;
        public TextView re_tv_star;
        public Button bt_update;


        public RE(View itemView) {
            super(itemView);
            re_im_head=(ImageView)itemView.findViewById(R.id.my_resource_im_head);
            re_tv_title=(TextView)itemView.findViewById(R.id.my_resource_tv_title);
            re_tv_jifen=(TextView)itemView.findViewById(R.id.my_resource_tv_jifen);
            re_tv_like=(TextView)itemView.findViewById(R.id.my_resource_tv_chaozan);
            re_tv_star=(TextView)itemView.findViewById(R.id.my_resource_tv_star);
            bt_update=(Button)itemView.findViewById(R.id.my_resource_bt_update);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public void getLikeStarNo(Resource resource,FouthFLisentener lisentener){
        BmobQuery<UserAction> query = new BmobQuery<UserAction>();
        query.addWhereEqualTo("resource", resource);
        query.findObjects(new FindListener<UserAction>() {
            @Override
            public void done(List<UserAction> list, BmobException e) {
                if (e == null) {
                    int likeNo = 0;
                    int starNo = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getActionType().equals("like"))
                            likeNo++;
                        else if (list.get(i).getActionType().equals("star"))
                            starNo++;
                    }
                    lisentener.onSeccess(likeNo,starNo,0);
                } else {
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:", e.getMessage());
                }
            }
        });
    }
}
