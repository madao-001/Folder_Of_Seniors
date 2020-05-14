package ncu.folder_of_seniors.module.ui.view;

import cn.bmob.v3.BmobUser;
import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.User;

public interface RegisterView extends BaseView {

    /**
     * 获取注册方式
     */
    String getType();
    /**
     * 获取用户信息
     */
    User getUser();
    /**
     * 注册成功时调用此接口
     */
    void onRegisterSeccess(String msg);
    /**
     * 注册失败时调用此接口
     */
    void onRegisterFails(String msg);
    /**
     * 发送验证码成功时调用此接口
     */
    void onSmsSendSeccess(String msg);
    /**
     * 发送验证码失败时调用此接口
     */
    void onSmsSendFails(String msg);

}
