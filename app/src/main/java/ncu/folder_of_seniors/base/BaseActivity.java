package ncu.folder_of_seniors.base;

import android.os.Bundle;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ncu.folder_of_seniors.app.MyApplication;

/**
 * @author oywj
 */
public abstract class BaseActivity<V extends BaseView> extends RxAppCompatActivity{

    private final static String TAG=BaseActivity.class.getName();
    protected Unbinder unbinder;
    protected abstract int getLayoutId();
    protected abstract void init();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        MyApplication.addActivity(this);
        unbinder=ButterKnife.bind(this);
        /**
         * onDestory()时， 自动解除订阅,为防止rxJava内存泄漏
         * 在onStart时候订阅，则自动会在onPause时候解除，
         * 如果在onCreate时候订阅，则会自动在onDestory时候解除
         * Flowable.interval(0, 1, TimeUnit.SECONDS)////参数一：延时0秒；参数二：每隔1秒开始执行
         **/
//        Observable.interval(1, TimeUnit.SECONDS)
//                .doOnDispose(new Action() {
//                    @Override
//                    public void run() throws Exception {
////                        Log.i(TAG, "Unsubscribing subscription from onCreate()");
//                    }
//                })
//                .compose(this.<Long>bindToLifecycle())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
////                        Log.i(TAG, "Started in onCreate(), running until onDestory(): " + aLong);
//                    }
//                });

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
