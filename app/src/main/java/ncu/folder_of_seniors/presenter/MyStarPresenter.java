package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.ChangeModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.MyStarModel;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.module.ui.view.MyStarView;
import ncu.folder_of_seniors.presenter.impl.ChangePresenterImpl;
import ncu.folder_of_seniors.presenter.impl.MyStarPresenterImpl;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class MyStarPresenter extends BasePresenter<MyStarView, MyStarModel> implements MyStarPresenterImpl {

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
        getModel().getData(clientUser, new FirstFLisentener() {
            @Override
            public void onSeccess(List<Resource> list) {
                getView().showData(list);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void removeStar(Resource resource) {
        getModel().removeStar(resource,clientUser, new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getView().showErrorMessage(msg);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }
}
