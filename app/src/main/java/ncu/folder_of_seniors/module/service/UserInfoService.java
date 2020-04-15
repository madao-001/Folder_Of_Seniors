package ncu.folder_of_seniors.module.service;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.utils.ToastEx;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;


public class UserInfoService implements BaseView {
    private Context context;
    public UserInfoService(Context context){
        this.context=context;
    }

    public void register(BmobUser user) {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    ToastEx.init(context, ToastEx.Type.SUCCESS,"注册成功", Toast.LENGTH_LONG,new Point(0,0)).show();
                }else{
                    ToastEx.init(context, ToastEx.Type.FAIL,"注册失败",Toast.LENGTH_LONG,new Point(0,0)).show();
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    public void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("error","Newest UserInfo is " + s);
                } else {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    public void updateUserInfo(BmobUser newUser) {
        newUser.update(clientUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastEx.init(context, ToastEx.Type.SUCCESS,"修改成功",Toast.LENGTH_LONG,new Point(0,0)).show();
                }else{
                    ToastEx.init(context, ToastEx.Type.FAIL,"修改失败"+e.getMessage(),Toast.LENGTH_LONG,new Point(0,0)).show();;
                }
            }
        });
    }

    @Override
    public Context getContext() {
        return null;
    }
}
