package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ThirdFLisentener;
import ncu.folder_of_seniors.model.MyLaunchModel;
import ncu.folder_of_seniors.model.ThirdFModel;
import ncu.folder_of_seniors.module.entity.Conversation;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.MyLaunchView;
import ncu.folder_of_seniors.module.ui.view.ThirdFView;
import ncu.folder_of_seniors.presenter.impl.MyLaunchPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.ThirdFPresenterImpl;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class ThirdFPresenter extends BasePresenter<ThirdFView, ThirdFModel> implements ThirdFPresenterImpl {

    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }

    @Override
    public void showData() {
        getModel().getData(new ThirdFLisentener() {
            @Override
            public void onSeccess(List<Conversation> list) {
                getView().showData(list);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

}
