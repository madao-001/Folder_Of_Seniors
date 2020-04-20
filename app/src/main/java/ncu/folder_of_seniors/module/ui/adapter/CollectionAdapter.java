package ncu.folder_of_seniors.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.utils.Utils;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CO> implements View.OnLongClickListener {
    private Context context;
    private List<Resource> resourceList=new ArrayList<Resource>();
    private CollectionAdapter.OnItemClickListener mOnItemClickListener;
    private CollectionAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private Resource resource;
    private User seller;
    public LinearLayout ll_ll;


    public CollectionAdapter(Context context, List<Resource> resourceList) {

        this.context=context;
        this.resourceList=resourceList;
        //Toast.makeText(context,"进入adapter"+sellList.size(),Toast.LENGTH_SHORT).show();
    }
    @Override
    public CollectionAdapter.CO onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_item, parent, false);
        return new CollectionAdapter.CO(v);
    }

    @Override
    public void onBindViewHolder(final CollectionAdapter.CO holder, final int position) {
        //Toast.makeText(context,"开始赋值操作"+sellList.size(),Toast.LENGTH_SHORT).show();

        //进行赋值操作
        resource=resourceList.get(position);
        seller = resource.getCreator();
        ((CO)holder).iv_username.setText(seller.getUsername());
        ((CO)holder).iv_points.setText(""+resource.getPrice());
        ((CO)holder).iv_title.setText(resource.getTitle());
        Glide.with(context).load(seller.getIcon()).into(((CO)holder).ie_head);
        if (resource.getPhotos().size()!=0){
            int a = resource.getPhotos().size();
            ((CO)holder).ll_ll.removeAllViews();
            for (int j=0;j<a;j++){
                ImageView imageView=new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.getWidth(context)/3,Utils.getHeight(context)/3));
                Glide.with(context).load(resource.getPhotos().get(j)).into(imageView);
                ((CO)holder).ll_ll.addView(imageView);
            }
        }
//

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

    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }


    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public class CO extends RecyclerView.ViewHolder {

        public ImageView ie_head;
        public TextView iv_username;
        public TextView iv_points;
        public TextView iv_title;
        public LinearLayout ll_ll;
        public CO(View itemView) {
            super(itemView);
            ie_head=(ImageView)itemView.findViewById(R.id.collection_item_ie_head);
            iv_username=(TextView)itemView.findViewById(R.id.collection_item_iv_username);
            iv_points=(TextView)itemView.findViewById(R.id.collection_item_iv_point);
            iv_title=(TextView)itemView.findViewById(R.id.collection_item_iv_title);
            ll_ll=(LinearLayout)itemView.findViewById(R.id.collection_item_sell_item_ll);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(CollectionAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(CollectionAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}
