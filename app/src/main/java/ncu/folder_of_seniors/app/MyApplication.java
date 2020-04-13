package ncu.folder_of_seniors.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;

import static ncu.folder_of_seniors.utils.StaticClass.BMOB_APP_ID;

/**
 * @author oywj
 */

public class MyApplication extends Application {

    public static Context appContext;
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    public static MyApplication clientApp;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        clientApp = this;
        Bmob.initialize(this, BMOB_APP_ID);
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
}