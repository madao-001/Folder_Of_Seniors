package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FirstFModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener2;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.FirstFView;
import ncu.folder_of_seniors.presenter.impl.FirstFPresenterImpl;
import static ncu.folder_of_seniors.app.MyApplication.resources;
import static ncu.folder_of_seniors.app.MyApplication.reviews;
import static ncu.folder_of_seniors.app.MyApplication.userActions;
import static ncu.folder_of_seniors.app.MyApplication.users;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class FirstFPresenter extends BasePresenter<FirstFView, FirstFModel> implements FirstFPresenterImpl {
    @Override
    public void getData() {
        getModel().showData(new FirstFLisentener() {
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
    public void getAllData() {
        getModel().showAllData(new BaseLisentener() {
            @Override
            public void onSeccess() {
                if(users.size()!=0&&resources.size()!=0&&userActions.size()!=0&&reviews.size()!=0)
                    getView().showAllData();
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
