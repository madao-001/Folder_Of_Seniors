package ncu.folder_of_seniors.presenter;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.LaunchModel;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.SellEditModel;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.LaunchView;
import ncu.folder_of_seniors.module.ui.view.SellEditView;
import ncu.folder_of_seniors.presenter.impl.LaunchPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.SellEditPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class SellEditPresenter extends BasePresenter<SellEditView, SellEditModel> implements SellEditPresenterImpl {

    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }


    @Override
    public void getResource(String resourceId) {
        getModel().getResource(resourceId, new ResouceDetailsLisentener() {
            @Override
            public void onSeccess(Resource resource) {
                getView().showResource(resource);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void updateResource(Resource resource) {
        String filePath = getView().getFilePath();
        getView().onLoading();
        getModel().udpateResource(resource, filePath, new RegisterLisentener() {
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
}
