package ncu.folder_of_seniors.module.ui.view;

import ncu.folder_of_seniors.base.BaseView;

public interface LoginView extends BaseView {

    /**
     * 获取用户名
     */
    String getUserName();
    /**
     * 获取密码
     */
    String getPassword();
    /**
     * 登入成功时调用此接口
     */
    void onLoginSeccess(String msg);
    /**
     * 登入失败时调用此接口
     */
    void onLoginFails(String msg);

}
