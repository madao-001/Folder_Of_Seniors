package ncu.folder_of_seniors.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.LoginModel;
import ncu.folder_of_seniors.model.RegisterModel;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.ui.view.LoginView;
import ncu.folder_of_seniors.module.ui.view.RegisterView;
import ncu.folder_of_seniors.presenter.impl.LoginPresenterImpl;
import ncu.folder_of_seniors.presenter.impl.RegisterPresenterImpl;
import ncu.folder_of_seniors.utils.MsgEnum;
import ncu.folder_of_seniors.utils.Verify;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class RegisterPresenter extends BasePresenter<RegisterView, RegisterModel> implements RegisterPresenterImpl {

    @Override
    public String userVerifyWithPhone(String username, String password, String password2, String age, Boolean sex, String phoneNo, String smsCode,String school) {
        if(Verify.isStrEmpty(phoneNo)){
            return "手机号不能为空！";
        }else if(!Verify.isMobile(phoneNo)){
            return "请输入正确的手机号！";
        }else if(Verify.isStrEmpty(username)){
            return "用户名不能为空！";
        }else if (!Verify.isUserName(username)){
            return "用户名由大小写字母开头，且不少于六个字符！";
        }else if(Verify.isStrEmpty(age)){
            return "请输入年龄！";
        }else if(!Verify.isAge(age)){
            return "请输入正确的年龄！";
        }else if(Verify.isStrEmpty(password)){
            return "密码不能为空！";
        }else if (!Verify.isConsistsOfNum_Letter(password)){
            return "密码仅由字母，下划线，或数字组成！";
        }else if(Verify.isStrEmpty(password2)){
            return "请输入确认密码！";
        }else if(!password.equals(password2)){
            return "两次输入密码不一致！";
        }else if(Verify.isStrEmpty(school)){
            return "请输入学校名称！";
        }else if(!Verify.isChinese(school)){
            return "请输入正确的学校名称！";
        }else if(sex == null){
            return "请选择性别！";
        }else if(Verify.isStrEmpty(smsCode)){
            return "请输入手机验证码！";
        }
        return MsgEnum.SUCCESS+"";
    }

    @Override
    public String userVerifyWithMail(String username, String password, String password2, String age, Boolean sex, String email,String school) {
        if(Verify.isStrEmpty(email)){
            return "邮箱地址不能为空！";
        }else if(!Verify.isEmail(email)){
            return "请输入正确的邮箱！";
        } else if(Verify.isStrEmpty(username)){
            return "用户名不能为空！";
        }else if (!Verify.isUserName(username)){
            return "用户名由大小写字母开头，且不少于六个字符！";
        }else if(Verify.isStrEmpty(age)){
            return "请输入年龄！";
        }else if(!Verify.isAge(age)){
            return "请输入正确的年龄！";
        }else if(Verify.isStrEmpty(password)){
            return "密码不能为空！";
        }else if (!Verify.isConsistsOfNum_Letter(password)){
            return "密码仅由字母，下划线，或数字组成！";
        }else if(Verify.isStrEmpty(password2)){
            return "请输入确认密码！";
        }else if(!password.equals(password2)){
            return "两次输入密码不一致！";
        }else if(Verify.isStrEmpty(school)){
            return "请输入学校名称！";
        }else if(sex == null){
            return "请选择性别！";
        }
        return MsgEnum.SUCCESS+"";
    }

    @Override
    public void sendSmsCode(String phoneNo) {
        getModel().requestSMSCode(phoneNo, new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                getView().onSmsSendSeccess(msg);
            }

            @Override
            public void onFails(String msg) {
                getView().onSmsSendFails(msg);
            }
        });
    }

    @Override
    public void registerWithPhone(String smsCode) {
        User user = getView().getUser();
        getModel().verifySmsCode(user.getMobilePhoneNumber(), smsCode, new BaseLisentener() {
            @Override
            public void onSeccess() {
                getModel().uploadIcon(user.getIcon(), new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {
                        user.setIcon(msg);
                        getModel().registerByPhone(user,smsCode,new RegisterLisentener() {
                            @Override
                            public void onSeccess(String msg) {
                                getView().onRegisterSeccess(msg);
                            }
                            @Override
                            public void onFails(String msg) {
                                getView().onRegisterFails(msg);
                            }
                        });
                    }

                    @Override
                    public void onFails(String msg) {
                        getView().onRegisterFails(msg);
                    }
                });
            }
            @Override
            public void onFails(String msg) {
                getView().onRegisterFails(msg);
            }
        });
    }

    @Override
    public void registerWithEmail() {
        User user = getView().getUser();
        getModel().uploadIcon(user.getIcon(), new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                user.setIcon(msg);
                getModel().registerByEmail(user, new RegisterLisentener() {
                    @Override
                    public void onSeccess(String msg) {
                        getView().onRegisterSeccess(msg);
                    }

                    @Override
                    public void onFails(String msg) {
                        getView().onRegisterFails(msg);
                    }
                });
            }

            @Override
            public void onFails(String msg) {
                getView().onRegisterFails(msg);
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
