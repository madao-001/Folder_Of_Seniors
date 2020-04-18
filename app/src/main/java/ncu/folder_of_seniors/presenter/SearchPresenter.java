package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FirstFModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.SearchModel;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.FirstFView;
import ncu.folder_of_seniors.module.ui.view.SearchView;
import ncu.folder_of_seniors.presenter.impl.FirstFPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.SearchPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.SettingPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class SearchPresenter extends BasePresenter<SearchView, SearchModel> implements SearchPresenterImpl {
    @Override
    public void getData() {
        getModel().showData(getView().getType(),getView().getKeyWord(),new FirstFLisentener() {
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
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }
}
