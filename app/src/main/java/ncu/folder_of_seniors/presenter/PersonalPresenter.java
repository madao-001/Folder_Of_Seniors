package ncu.folder_of_seniors.presenter;

import android.util.Log;

import java.util.List;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FirstFModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener2;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener3;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.PersonalModel;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.FirstFView;
import ncu.folder_of_seniors.module.ui.view.PersonalView;
import ncu.folder_of_seniors.presenter.impl.FirstFPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.PersonalPresenterImpl;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class PersonalPresenter extends BasePresenter<PersonalView, PersonalModel> implements PersonalPresenterImpl {

    @Override
    public void getData(String userid) {
        getModel().findUser(userid,new PersonalLisentener() {
            @Override
            public void onSeccess(User user) {
                getModel().findResourceList(user, new FirstFLisentener() {
                    @Override
                    public void onSeccess(List<Resource> list) {
                        getModel().findFollow(user, new PersonalLisentener2() {
                            @Override
                            public void onSeccess(int num1, int num2, Boolean isFollowing) {
                                getModel().findLikesNo(user, new PersonalLisentener3() {
                                    @Override
                                    public void onSeccess(Integer num) {
                                        getView().showLikesNo(num);
                                        getView().showUserInfo(user);
                                        getView().showResourceList(list);
                                        getView().showFollow(num1,num2,isFollowing);
                                    }

                                    @Override
                                    public void onFails(String msg) {
                                        getView().showUserInfo(user);
                                        getView().showResourceList(list);
                                        getView().showFollow(num1,num2,isFollowing);
                                        getView().showErrorMessage(msg);
                                    }
                                });
                            }
                            @Override
                            public void onFails(String msg) {
                                getView().showUserInfo(user);
                                getView().showResourceList(list);
                                getView().showErrorMessage(msg);
                            }
                        });
                    }

                    @Override
                    public void onFails(String msg) {
                        getView().showUserInfo(user);
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
    public void addFollowing(User person) {
        getModel().addFollowing(person,new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getModel().addFollowers(person,new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {
                        getView().showChangeFollow(true);
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
    public void removeFollowing(User person) {
        getModel().removeFollowing(person,new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getModel().removeFollowers(person,new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {
                        getView().showChangeFollow(false);
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
