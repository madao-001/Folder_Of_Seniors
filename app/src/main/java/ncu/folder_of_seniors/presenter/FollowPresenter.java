package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.ChangeModel;
import ncu.folder_of_seniors.model.FollowModel;
import ncu.folder_of_seniors.model.Lisentener.FollowLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.module.ui.view.FollowView;
import ncu.folder_of_seniors.presenter.impl.ChangePresenterImpl;
import ncu.folder_of_seniors.presenter.impl.FollowPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class FollowPresenter extends BasePresenter<FollowView, FollowModel> implements FollowPresenterImpl {


    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }

    @Override
    public void showData(String type) {
        getModel().getFollow(type, new FollowLisentener() {
            @Override
            public void onSeccess(List<User> users) {
                getView().showData(users);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }
}
