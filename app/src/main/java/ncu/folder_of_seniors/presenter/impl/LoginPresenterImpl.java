package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.User;

public interface LoginPresenterImpl extends BasePresenterImpl {

    /**
     * 输入验证
     */
    public String userVerify(String username,String password);

    /**
     * 登录
     */
    public void login();

    /**
     * 退出
     */
    public void logout();
}
