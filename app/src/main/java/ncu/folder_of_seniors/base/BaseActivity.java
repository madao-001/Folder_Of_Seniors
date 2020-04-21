package ncu.folder_of_seniors.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ncu.folder_of_seniors.R;
import ncu.folder_of_seniors.app.MyApplication;
import ncu.folder_of_seniors.module.entity.MessageEvent;

/**
 * @author oywj
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView{

    private final static String TAG=BaseActivity.class.getName();
    protected Unbinder unbinder;
    private AlertDialog alertDialog;
    /**
     * 保存使用注解的 Presenter ，用于解绑
     */
    private List<BasePresenter> mInjectPresenters;
    protected abstract int getLayoutId();
    protected abstract void initViews();
    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        MyApplication.addActivity(this);
        unbinder=ButterKnife.bind(this);

        mInjectPresenters = new ArrayList<>();

        //获得已经申明的变量，包括私有的
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(this);
                    field.setAccessible(true);
                    field.set(this, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }catch (ClassCastException e){
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }
        initData();
        initViews();
    }

    public void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    /**
     * EventBus处理事件
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(MessageEvent messageEvent) {
        //更新UI数据
        Log.e("will changed class name", "classname:" + messageEvent.className);
        Log.e("this class name", "classname:" + getClass().getName());
        if (messageEvent.className.contains(getClass().getName())) {
            initData();
        }
    }

    /**
     * EventBus更新UI
     */
    public void onChangeDataInUI(String className) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.className = className;
        EventBus.getDefault().post(messageEvent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * 解绑，避免内存泄漏
         */
        for (BasePresenter presenter : mInjectPresenters) {
            presenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
        unbinder.unbind();
        //反注册EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

}
