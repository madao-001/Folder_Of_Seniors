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
import ncu.folder_of_seniors.module.ui.ResourceDetailsActivity;
import ncu.folder_of_seniors.utils.Utils;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PA> {
    private List<Resource> resourceList;
    private Context context;
    private Resource resource;


    public PersonalAdapter(Context context, List<Resource> resourceList) {
        this.context=context;
        this.resourceList=resourceList;
    }

     class PA extends RecyclerView.ViewHolder {
        public final LinearLayout ll_ll;
        public final ImageView iv_head;
        public final TextView tv_nickName;
        public final TextView tv_title;
        public final TextView tv_content;
        public final TextView tv_price;
        public final TextView tv_time;
         public final TextView tv_comment;
         public final TextView tv_like;
        public PA(View itemView) {
            super(itemView);
            iv_head=(ImageView) itemView.findViewById(R.id.resource_item_head);
            tv_nickName=(TextView)itemView.findViewById(R.id.resource_item_nickName);
            tv_title=(TextView)itemView.findViewById(R.id.resource_item_title);
            tv_content=(TextView)itemView.findViewById(R.id.resource_item_content);
            tv_price=(TextView) itemView.findViewById(R.id.resource_item_price);
            tv_time=(TextView) itemView.findViewById(R.id.resource_item_time);
            ll_ll=(LinearLayout) itemView.findViewById(R.id.resource_item_ll);
            tv_comment=(TextView)itemView.findViewById(R.id.resource_tv_comment);
            tv_like=(TextView)itemView.findViewById(R.id.resour_tv_like);
        }
    }

    @Override
    public PA onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_item, parent, false);
        return new PersonalAdapter.PA(v);
    }

    @Override
    public void onBindViewHolder(final PA holder, final int position) {
        resource=resourceList.get(position);
        holder.tv_nickName.setText(resource.getCreator().getUsername());
        if(resource.getTitle()!=null){
            holder.tv_title.setText(resource.getTitle());
        }
        if(resource.getDesc()!=null){
            holder.tv_content.setText(resource.getDesc());
        }
        holder.tv_price.setText(resource.getPrice()+"");
        holder.tv_time.setText(resource.getUpdatedAt()+"");
        holder.tv_comment.setText("0");
        holder.tv_like.setText(resource.getLikes()+"");
        Glide.with(context).load(resource.getCreator().getIcon()).into(holder.iv_head);

        if(resource.getPhotos().size()!=0) {
            int a = resource.getPhotos().size();
            ((PA)holder).ll_ll.removeAllViews();
            for (int j = 0; j < a; j++) {
                    /*
                    动态添加会导致一个问题，重复添加，导致内存泄漏
                    所以为了防止重复添加，现将所有的imageview移除，清空资源，再添加
                     */
                final ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(Utils.getWidth(context) / 3, Utils.getHeight(context) / 3));
                Glide.with(context).load(resource.getPhotos().get(j)).into(imageView);
                ((PA)holder).ll_ll.addView(imageView);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource resource = resourceList.get(position);
                //Toast.makeText(mContext,"name is "+resource.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ResourceDetailsActivity.class);
                intent.putExtra("objectId",resource.getObjectId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }
}
