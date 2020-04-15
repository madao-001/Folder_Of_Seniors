package ncu.folder_of_seniors.base;

import android.content.Context;
import android.os.Bundle;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ncu.folder_of_seniors.app.MyApplication;

/**
 * @author oywj
 */
public abstract class BaseActivity extends RxAppCompatActivity implements BaseView{

    private final static String TAG=BaseActivity.class.getName();
    protected Unbinder unbinder;
    /**
     * 保存使用注解的 Presenter ，用于解绑
     */
    private List<BasePresenter> mInjectPresenters;
    protected abstract int getLayoutId();
    protected abstract void initViews();
    protected abstract void initData();

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

        initViews();
        initData();
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
    }

    @Override
    public Context getContext() {
        return this;
    }

}
