package ncu.folder_of_seniors.presenter;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.ChangeModel;
import ncu.folder_of_seniors.model.LaunchModel;
import ncu.folder_of_seniors.model.Lisentener.LaunchLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.view.ChangeView;
import ncu.folder_of_seniors.module.ui.view.LaunchView;
import ncu.folder_of_seniors.presenter.impl.ChangePresenterImpl;
import ncu.folder_of_seniors.presenter.impl.LaunchPresenterImpl;
import ncu.folder_of_seniors.utils.Verify;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class LaunchPresenter extends BasePresenter<LaunchView, LaunchModel> implements LaunchPresenterImpl {

    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }

    @Override
    public void launch(Resource resource) {
        String filePath = getView().getFilePath();
        List<LocalMedia> list = getView().getPicPath();
        String[] filePaths = new String[list.size()+1];
        filePaths[0] = filePath;
        for (int i = 0; i <list.size() ; i++) {
            filePaths[i + 1] = list.get(i).getPath();
        }
        getView().onLoading();
        getModel().launchResource(resource,filePaths ,new RegisterLisentener() {
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
