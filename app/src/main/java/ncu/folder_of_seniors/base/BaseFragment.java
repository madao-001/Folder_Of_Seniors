package ncu.folder_of_seniors.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author oywj
 */
public abstract class BaseFragment extends RxFragment {

    private final static String TAG=BaseActivity.class.getName();

    protected View view;
    protected Unbinder unbinder;
    protected abstract int getLayoutId();
    protected abstract void init();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view=inflater.inflate(getLayoutId(), container, false);
        unbinder=ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
