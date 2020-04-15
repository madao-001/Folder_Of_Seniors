package ncu.folder_of_seniors.presenter;

import android.util.Log;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.ChangeModel;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.SettingModel;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.module.ui.view.SettingView;
import ncu.folder_of_seniors.presenter.impl.SettingPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class SettingPresenter extends BasePresenter<SettingView, SettingModel> implements SettingPresenterImpl {

    @Override
    public void logOut() {
        getModel().logOut(new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getView().showSuccessMessage(msg);
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
