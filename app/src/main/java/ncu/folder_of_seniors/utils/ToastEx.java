package ncu.folder_of_seniors.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ncu.folder_of_seniors.R;



public class ToastEx {
    public enum Type{
        SUCCESS, //成功
        FAIL,    //失败
        WARNING, //警告
        DOUBT    //疑问
    }

    public static Toast init(Context context, Type type, String msg, int duration, Point pos){
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View layout=inflater.inflate(R.layout.base_toast_msg,null);
        ImageView icon=(ImageView)layout.findViewById(R.id.toast_msg_icon);
        TextView text=(TextView)layout.findViewById(R.id.toast_msg_text);
        switch (type){
            case SUCCESS:
                icon.setImageResource(R.drawable.icon_success);
                break;
            case FAIL:
                icon.setImageResource(R.drawable.icon_fail);
                break;
            case WARNING:
                icon.setImageResource(R.drawable.icon_warning);
                break;
            case DOUBT:
                icon.setImageResource(R.drawable.icon_doubt);
                break;
            default:
                icon.setImageResource(R.drawable.icon_success);
                break;
        }
        text.setText(msg);
        Toast toast=new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,pos.x,pos.y);
        return toast;
    }
}
