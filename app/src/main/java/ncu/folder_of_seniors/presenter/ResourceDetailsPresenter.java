package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FirstFModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener2;
import ncu.folder_of_seniors.model.RegisterModel;
import ncu.folder_of_seniors.model.ResourceDetailsModel;
import ncu.folder_of_seniors.model.impl.ResourceDetailsModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.view.FirstFView;
import ncu.folder_of_seniors.module.ui.view.ResourceDetailsView;
import ncu.folder_of_seniors.presenter.impl.FirstFPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.ResourceDetailsPresenterImpl;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class ResourceDetailsPresenter extends BasePresenter<ResourceDetailsView, ResourceDetailsModel> implements ResourceDetailsPresenterImpl {
    @Override
    public void getData(String resourceId) {
        getModel().getResource(resourceId,new ResouceDetailsLisentener() {
            @Override
            public void onSeccess(Resource resource) {
                if(clientUser!=null){
                    getModel().getUserAction(resource, clientUser, new ResouceDetailsLisentener2() {
                        @Override
                        public void onSeccess(Boolean isLike, Boolean isStar, Boolean isBuy) {
                            getView().showData(resource,isLike,isStar,isBuy);
                        }
                        @Override
                        public void onFails(String msg) {
                            getView().showData(resource,false,false,false);
                        }
                    });
                }else {
                    getView().showData(resource,false,false,false);
                }
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void setUserAction(Resource resource, User user, String type,Boolean isAdd) {
        UserAction userAction = new UserAction();
        userAction.setResource(resource);
        userAction.setUser(user);
        userAction.setActionType(type);
        if(isAdd){
            if(type.equals("like")){
                getModel().changeLike(resource, 1, new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {

                    }

                    @Override
                    public void onFails(String msg) {
                        getView().showErrorMessage(msg);
                    }
                });
            }else if(type.equals("buy")){
                getModel().updatePoints(user.getObjectId(), resource, new BaseLisentener() {
                    @Override
                    public void onSeccess() {
                        getModel().updateResource(resource, new BaseLisentener() {
                            @Override
                            public void onSeccess() {

                            }

                            @Override
                            public void onFails(String msg) {
                                getView().showErrorMessage(msg);
                            }
                        });
                    }

                    @Override
                    public void onFails(String msg) {
                        getView().showErrorMessage("购买失败："+msg);
                    }
                });
            }
            getModel().setUserAction(userAction, new RegisterLisentener() {
                @Override
                public void onSeccess(String msg) {
                    getView().showActionResult(type,isAdd);
                }

                @Override
                public void onFails(String msg) {
                    getView().showErrorMessage(msg);
                }
            });
        }else {
            if(type.equals("like")){
                getModel().changeLike(resource, -1, new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {

                    }

                    @Override
                    public void onFails(String msg) {
                        getView().showErrorMessage(msg);
                    }
                });
            }
            getModel().deleteUserAction(userAction, new RegisterLisentener() {
                @Override
                public void onSeccess(String msg) {
                    getView().showActionResult(type,isAdd);
                }

                @Override
                public void onFails(String msg) {
                    getView().showErrorMessage(msg);
                }
            });
        }

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
