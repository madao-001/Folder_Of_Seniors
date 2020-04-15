package ncu.folder_of_seniors.base;

import android.util.Log;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public abstract class BasePresenter<V extends BaseView,M extends BaseModel> implements BasePresenterImpl{
    private SoftReference<BaseView> mReferenceView;
    private V mProxyView;
    private M mModel;

    @SuppressWarnings({"unchecked","TryWithIdenticalCatches"})
    @Override
    public void attach(BaseView view) {
        //使用软引用创建对象
        mReferenceView = new SoftReference<>(view);
        //使用动态代理做统一的逻辑判断 aop思想
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (mReferenceView == null || mReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(mReferenceView.get(), objects);
            }
        });

        //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        //Log.e("===========", "attach: "+this.getClass().getSimpleName());
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public V getView() {
        return mProxyView;
    }

    protected M getModel() {
        return mModel;
    }

    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
    }

}
