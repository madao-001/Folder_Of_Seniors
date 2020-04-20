package ncu.folder_of_seniors.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.utils.UtilTools;


class ViewHolder{
    public ImageView follow_head;
    public TextView follow_name;
    public ImageView follow_sex;
    public TextView follow_school;
    public TextView follow_time;


    View itemView;
    public ViewHolder(View itemView){
        if (itemView==null){
            throw new IllegalArgumentException("itemView can not be null!");
        }
        this.itemView=itemView;
        follow_head=(ImageView)itemView.findViewById(R.id.follow_head);
        follow_name=(TextView)itemView.findViewById(R.id.follow_name);
        follow_sex=(ImageView)itemView.findViewById(R.id.follow_sex);
        follow_school=(TextView)itemView.findViewById(R.id.follow_school);
        follow_time=(TextView)itemView.findViewById(R.id.follow_time);

    }
}


public class FollowAdapter extends BaseAdapter {
    private Context context;
    private List<User> userInfoList;
    private ViewHolder holder = null;
    private LayoutInflater layoutInflater;


    public FollowAdapter(Context context, List<User> userInfoList) {
        this.context=context;
        this.userInfoList=userInfoList;


        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return userInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfoList.get(position).getObjectId();
    }


    public void remove(int index) {
        userInfoList.remove(index);
    }

    public void refreshDataSet() {
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.follow_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(userInfoList.get(position).getIcon()).into(holder.follow_head);
        holder.follow_time.setText(userInfoList.get(position).getCreatedAt());
        if(userInfoList.get(position).isSex())
            holder.follow_school.setText("他来自"+userInfoList.get(position).getSchool());
        else
            holder.follow_school.setText("她来自"+userInfoList.get(position).getSchool());
        holder.follow_name.setText(userInfoList.get(position).getUsername());

        if (userInfoList.get(position).isSex()){
            holder.follow_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.man));
        }else {
            holder.follow_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.woman));
        }
        return convertView;
    }
}
