package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.ChangeModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.MyBoughtSelledLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.MyBoughtSelledModel;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.module.ui.view.MyBoughtSelledView;
import ncu.folder_of_seniors.presenter.impl.ChangePresenterImpl;
import ncu.folder_of_seniors.presenter.impl.MyBoughtSelledPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class MyBoughtSelledPresenter extends BasePresenter<MyBoughtSelledView, MyBoughtSelledModel> implements MyBoughtSelledPresenterImpl {
    List<UserAction> userActions = new ArrayList<>();

    @Override
    public void getData(String type, User user) {
        getModel().getData(type, user, new MyBoughtSelledLisentener() {
            @Override
            public void onSeccess(List<UserAction> userActionList) {
                userActions.addAll(userActionList);
                getView().showData(userActionList);
            }

            @Override
            public void onProgress(List<UserAction> list) {
                userActions.addAll(list);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }
}
