package ncu.folder_of_seniors.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.event.RefreshEvent;
import ncu.folder_of_seniors.module.receiver.MyMessageHandler;
import ncu.folder_of_seniors.utils.Verify;

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
        if (getApplicationInfo().packageName.equals(getMyProcessName())){
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new MyMessageHandler(appContext));
        }
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        clientUser =  BmobUser.getCurrentUser(User.class);
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

    public static void fetchUserInfo(BaseLisentener lisentener){
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    clientUser =  BmobUser.getCurrentUser(User.class);
                    if(clientUser!=null){
                        IS_LOGIN = true;
                        connectBmobIm();
                        lisentener.onSeccess();

                    }else {
                        IS_LOGIN = false;
                    }
                } else {
                    lisentener.onFails(e.getMessage());
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 连接到服务器
     * @return
     */
    public static void connectBmobIm() {
        //登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        //判断用户是否登录，并且连接状态不是已连接，则进行连接操作
        if (!Verify.isStrEmpty(clientUser.getObjectId())&&
                BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(clientUser.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                        BmobIM.getInstance().
                                updateUserInfo(new BmobIMUserInfo(clientUser.getObjectId(),
                                        clientUser.getUsername(), clientUser.getIcon()));
                        EventBus.getDefault().post(new RefreshEvent());
                    } else {
                        //连接失败
                        Toast.makeText(appContext,"连接服务器失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
            BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                @Override
                public void onChange(ConnectionStatus status) {
                    //Toast.makeText(appContext,status.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.e("error",BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}