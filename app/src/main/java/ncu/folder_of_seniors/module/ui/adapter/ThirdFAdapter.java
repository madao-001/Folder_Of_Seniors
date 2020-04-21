package ncu.folder_of_seniors.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.module.entity.Conversation;
import ncu.folder_of_seniors.utils.TimeUtil;
import ncu.folder_of_seniors.utils.Verify;

public class ThirdFAdapter extends RecyclerView.Adapter<ThirdFAdapter.RV> implements View.OnLongClickListener{
    private List<Conversation> conversationList=new ArrayList<Conversation>();
    private Context context;
    private Conversation conversation;


    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public ThirdFAdapter(Context context, List<Conversation> conversationList) {
        this.context=context;
        this.conversationList=conversationList;
    }

    @Override
    public RV onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_fragment_item, parent, false);
        return new ThirdFAdapter.RV(v);
    }

    @Override
    public void onBindViewHolder(final RV holder, final int position) {
        //赋值操作
        conversation=conversationList.get(position);
        //会话图标
        Object obj = conversation.getAvatar();
        if(obj instanceof String){
            String avatar=(String)obj;
            Glide.with(context).load(conversation.getAvatar()).into(holder.iv_recent_avatar);
        }else{
            int defaultRes = (int)obj;
            holder.iv_recent_avatar.setImageResource(defaultRes);
        }
        if (conversation.getcName()!=null){
            ((RV)holder).tv_recent_name.setText(conversation.getcName());
        }
        if (!Verify.isStrEmpty(TimeUtil.getChatTime(false,conversation.getLastMessageTime()))){
            ((RV)holder).tv_recent_time.setText(TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
        }
        if (conversation.getLastMessageContent()!=null){
            ((RV)holder).tv_recent_msg.setText(conversation.getLastMessageContent());
        }
        long unread = conversation.getUnReadCount();
        if(unread>0){
            ((RV)holder).tv_recent_unread.setVisibility(View.VISIBLE);
            ((RV)holder).tv_recent_unread.setText(String.valueOf(unread));
        }else{
            ((RV)holder).tv_recent_unread.setVisibility(View.GONE);
        }

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

    /**
     * 删除数据
     * @param position
     */
    public void remove(int position) {
        conversationList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 获取指定position的Item
     * @param position
     * @return
     */
    public Conversation getItem(int position){
        return conversationList.get(position);
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    @Override
    public boolean onLongClick(View v) {

        return false;
    }


    public class RV extends RecyclerView.ViewHolder {
        public ImageView iv_recent_avatar;
        public TextView tv_recent_name;
        public TextView tv_recent_msg;
        public TextView tv_recent_time;
        public TextView tv_recent_unread;


        public RV(View itemView) {
            super(itemView);
            iv_recent_avatar=(ImageView)itemView.findViewById(R.id.iv_recent_avatar);
            tv_recent_name=(TextView)itemView.findViewById(R.id.tv_recent_name);
            tv_recent_msg=(TextView)itemView.findViewById(R.id.tv_recent_msg);
            tv_recent_time=(TextView)itemView.findViewById(R.id.tv_recent_time);
            tv_recent_unread=(TextView)itemView.findViewById(R.id.tv_recent_unread);
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
}
