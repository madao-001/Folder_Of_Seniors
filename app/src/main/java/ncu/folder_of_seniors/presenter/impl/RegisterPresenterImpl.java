package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;

public interface RegisterPresenterImpl extends BasePresenterImpl {

    /**
     * 输入验证1
     */
    public String userVerifyWithPhone(String username, String password,String password2,String age,Boolean sex,String phoneNo,String smsCode);

    /**
     * 输入验证2
     */
    public String userVerifyWithMail(String username, String password,String password2,String age,Boolean sex,String email);

    /**
     * 获取手机验证码
     */
    public void sendSmsCode(String phoneNo);

    /**
     * 手机号注册
     */
    public void registerWithPhone(String smsCode);

    /**
     * 邮箱注册
     */
    public void registerWithEmail();

}
