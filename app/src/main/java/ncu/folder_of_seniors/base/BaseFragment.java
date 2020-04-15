package ncu.folder_of_seniors.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author oywj
 */
public abstract class BaseFragment extends RxFragment implements BaseView{

    private final static String TAG=BaseActivity.class.getName();

    protected View view;
    protected Unbinder unbinder;
    private List<BasePresenter> mInjectPresenters;
    protected abstract int getLayoutId();
    protected abstract void init();
    protected abstract void initData();

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(getLayoutId(), container, false);
        unbinder=ButterKnife.bind(this, view);
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
                    //绑定
                    mInjectPresenter.attach(this);
                    field.setAccessible(true);
                    field.set(this, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }
        init();
        initData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        for (BasePresenter presenter : mInjectPresenters) {
            presenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
    }
}
