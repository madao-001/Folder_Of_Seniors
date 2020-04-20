package ncu.folder_of_seniors.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.service.UserInfoService;
import ncu.folder_of_seniors.utils.StaticClass;

import static ncu.folder_of_seniors.utils.StaticClass.BMOB_APP_ID;
import static ncu.folder_of_seniors.utils.StaticClass.IS_LOGIN;

/**
 * @author oywj
 */

public class MyApplication extends Application {

    public static Context appContext;
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    public static MyApplication clientApp;
    public static User clientUser;//当前登录用户

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        clientApp = this;
        Bmob.initialize(this, BMOB_APP_ID);
        fetchUserInfo();
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    public static Context getClientAppContext(){
        return appContext;
    }

    public static MyApplication getClientApplication(){
        return clientApp;
    }

    /**
     * 获取活动
     * @return
     */
    public static ArrayList<Activity> getActivityList(){
        return activityList;
    }

    /**
     * 添加Activity到容器中
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 清空Activity到容器中
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //遍历所有Activity并finish
    public static void removeAllActivity() {
        for(Activity activity:activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    //遍历所有Activity并finish
    public static void exit() {
        removeAllActivity();
    }

    public static void fetchUserInfo(){
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    clientUser =  BmobUser.getCurrentUser(User.class);;
                    if(clientUser!=null){
                        IS_LOGIN = true;
                    }else {
                        IS_LOGIN = false;
                    }
                } else {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }
}