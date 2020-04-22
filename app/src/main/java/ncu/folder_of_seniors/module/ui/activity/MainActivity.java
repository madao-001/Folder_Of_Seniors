package ncu.folder_of_seniors.module.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.base.BaseActivity;
import ncu.folder_of_seniors.base.BaseFragment;
import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.module.event.RefreshEvent;
import ncu.folder_of_seniors.module.ui.adapter.MyPagerAdapter;
import ncu.folder_of_seniors.module.ui.anim.DepthPageTransformer;
import ncu.folder_of_seniors.module.ui.fragment.FirstFragment;
import ncu.folder_of_seniors.module.ui.fragment.FouthFragment;
import ncu.folder_of_seniors.module.ui.fragment.SecondFragment;
import ncu.folder_of_seniors.module.ui.fragment.ThirdFragment;
import ncu.folder_of_seniors.module.ui.widget.BottomBar;
import ncu.folder_of_seniors.module.ui.widget.PopupMenuUtil;
import ncu.folder_of_seniors.module.ui.widget.SimpleToolBar;
import ncu.folder_of_seniors.utils.ToastEx;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;
import static ncu.folder_of_seniors.app.MyApplication.fetchUserInfo;

public class MainActivity extends BaseActivity implements BaseView {

    @BindView(R.id.bottomBar) BottomBar bottomBar;
    @BindView(R.id.center_img) ImageView mCenterImage;
    @BindView(R.id.simple_toolbar) SimpleToolBar simpleToolBar;
    @BindView(R.id.main_content) ViewPager viewPager;

    //当Android6.0系统以上时，动态获取权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
    };
    //权限的标志
    private static final int PERMISSION_CODES = 1001;
    private boolean permissionGranted = true;

    private BaseFragment FirstF,SecondF,ThirdF,FouthF;
    private List<Fragment> fragmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
        if (!permissionGranted) {
            Toast.makeText(this, "请打开权限", Toast.LENGTH_SHORT).show();
            return;
        }

        //初始化Fragment
        setStatusBar();
        fragmentList = new ArrayList<>();
        FirstF = new FirstFragment();
        SecondF = new SecondFragment();
        ThirdF = new ThirdFragment();
        FouthF = new FouthFragment();

        fragmentList.add(FirstF);
        fragmentList.add(SecondF);
        fragmentList.add(ThirdF);
        fragmentList.add(FouthF);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fragmentList));

        //设置toolbar
        simpleToolBar.setMainTitle("首页");

        //滑动效果
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        //设置监听
        bottomBar.setOnBottombarOnclick(new BottomBar.OnBottonbarClick() {
            @Override
            public void onFirstClick() {
                viewPager.setCurrentItem(0);
                simpleToolBar.setMainTitle("首页");
            }

            @Override
            public void onSecondClick() {
                viewPager.setCurrentItem(1);
                simpleToolBar.setMainTitle("同校");
            }

            @Override
            public void onThirdClick() {
                viewPager.setCurrentItem(2);
                simpleToolBar.setMainTitle("消息");
            }

            @Override
            public void onFouthClick() {
                viewPager.setCurrentItem(3);
                simpleToolBar.setMainTitle("个人中心");
            }

            @Override
            public void onCenterClick() {
                PopupMenuUtil.getInstance()._show(getApplication(), mCenterImage);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        bottomBar.performClickFirst();
                        break;
                    case 1:
                        bottomBar.performClickSecond();
                        break;
                    case 2:
                        bottomBar.performClickThird();
                        break;
                    case 3:
                        bottomBar.performClickFouth();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void initData() {
        //第一次运行建完表之后就可以删掉这行代码了
        //BmobUpdateAgent.initAppVersion();
        //设置仅WiFi环境更新
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        //设置更新监听器
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
                int a = updateStatus;
                UpdateResponse b = updateResponse;

            }
        });
        if (clientUser != null) {
            fetchUserInfo(new BaseLisentener() {
                @Override
                public void onSeccess() {

                }

                @Override
                public void onFails(String msg) {
                    ToastEx.init(getContext(), ToastEx.Type.FAIL,msg,Toast.LENGTH_LONG,new Point(0,0)).show();
                }
            });
        }
    }

    /**
     * 动态的进行权限请求
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        List<String> p = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                p.add(permission);
            }
        }
        if (p.size() > 0) {
            requestPermissions(p.toArray(new String[p.size()]), PERMISSION_CODES);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODES:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    permissionGranted = false;
                } else {
                    permissionGranted = true;
                }
        }
    }

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));//设置状态栏颜色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |               View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BmobIM.getInstance().clear();
        BmobIM.getInstance().disConnect();
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    //通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    //通知有离线消息接收
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    //通知有自定义消息接收
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        checkRedPoint();
    }

    @Override
    public void onEventBus(ncu.folder_of_seniors.module.entity.MessageEvent messageEvent) {
        super.onEventBus(messageEvent);
        checkRedPoint();
    }

    /**
     *
     */
    private void checkRedPoint() {
        //获取全部会话的未读消息数量
        int count = (int) BmobIM.getInstance().getAllUnReadCount();
        if (count > 0) {
            bottomBar.setConversationTips(true,count);
        } else {
            bottomBar.setConversationTips(false,0);
        }
    }
}
