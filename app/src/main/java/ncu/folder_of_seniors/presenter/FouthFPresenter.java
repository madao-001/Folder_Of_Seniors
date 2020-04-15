package ncu.folder_of_seniors.presenter;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FouthFModel;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.module.ui.view.FouthFView;
import ncu.folder_of_seniors.presenter.impl.FouthFPresenterImpl;
import ncu.folder_of_seniors.utils.MsgEnum;
import ncu.folder_of_seniors.utils.Verify;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class FouthFPresenter extends BasePresenter<FouthFView, FouthFModel> implements FouthFPresenterImpl {

    @Override
    public void showData() {
        getModel().showData(new FouthFLisentener() {
            @Override
            public void onSeccess(int num1, int num2,int num3) {
                getView().showData(num1,num2,num3);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void addFollowing() {
        getModel().addFollowing(new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getModel().addFollowers(new RegisterLisentener() {
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
