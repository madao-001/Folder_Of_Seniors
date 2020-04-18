package ncu.folder_of_seniors.module.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.PersonalActivity;
import ncu.folder_of_seniors.module.ui.ResourceDetailsActivity;
import ncu.folder_of_seniors.utils.Utils;

public class FirstFAdapter extends RecyclerView.Adapter<FirstFAdapter.VH>implements View.OnClickListener{

    private List<Resource> mDatas;
    private Context mContext;
    private Resource data;
    private User user;
    private FirstFAdapter.Callback mCallback;

    /**

     * 自定义接口，用于回调按钮点击事件到Activity

     */
    public interface Callback {

        public void click(View v);

    }


    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final LinearLayout ll_ll;
        public final ImageView iv_head;
        public final TextView tv_nickName;
        public final TextView tv_title;
        public final TextView tv_content;
        public final TextView tv_price;
        public final TextView tv_time;
        public final TextView tv_comment;
        public final TextView tv_like;
        public VH(View view) {
            super(view);
            iv_head=(ImageView) view.findViewById(R.id.resource_item_head);
            tv_nickName=(TextView)view.findViewById(R.id.resource_item_nickName);
            tv_title=(TextView)view.findViewById(R.id.resource_item_title);
            tv_content=(TextView)view.findViewById(R.id.resource_item_content);
            tv_price=(TextView) view.findViewById(R.id.resource_item_price);
            tv_time=(TextView) view.findViewById(R.id.resource_item_time);
            ll_ll=(LinearLayout) view.findViewById(R.id.resource_item_ll);
            tv_comment=(TextView)view.findViewById(R.id.resource_tv_comment);
            tv_like=(TextView)view.findViewById(R.id.resour_tv_like);
        }
    }

    public FirstFAdapter(Context mContext, List<Resource> data, FirstFAdapter.Callback callback) {
        this.mDatas = data;
        this.mContext = mContext;
        mCallback = callback;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        //设置数据
        data=mDatas.get(position);
        user = data.getCreator();
        holder.tv_nickName.setText(user.getUsername());
        holder.tv_title.setText(data.getTitle());
        if(data.getCreator().getIcon()!=null){
            Glide.with(mContext).load(data.getCreator().getIcon()).into(holder.iv_head);
        }
        holder.tv_content.setText(data.getDesc());
        holder.tv_price.setText(data.getPrice()+"");
        holder.tv_time.setText(data.getUpdatedAt()+"");
        holder.tv_comment.setText("0");
        holder.tv_like.setText(data.getLikes()+"");

        if(data.getPhotos().size()!=0) {
            int a = data.getPhotos().size();
            holder.ll_ll.removeAllViews();
            for (int j = 0; j < a; j++) {
                /*
                动态添加会导致一个问题，重复添加，导致内存泄漏
                所以为了防止重复添加，现将所有的imageview移除，清空资源，再添加
                 */
                final ImageView imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.getWidth(mContext) / 3, Utils.getHeight(mContext) / 3));

                Glide.with(mContext).load(data.getPhotos().get(j)).into(imageView);
                holder.ll_ll.addView(imageView);
            }

        }

        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource Resource = mDatas.get(position);
                //Toast.makeText(mContext,"进入个人主页！！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PersonalActivity.class);
                String userid = Resource.getCreator().getObjectId();
                intent.putExtra("objectId",userid);
                mContext.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource Resource = mDatas.get(position);
                //Toast.makeText(mContext,"name is "+Resource.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ResourceDetailsActivity.class);
                intent.putExtra("objectId",Resource.getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false);
        return new VH(v);
    }

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View view) {
        mCallback.click(view);
    }
}
