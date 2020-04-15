package ncu.folder_of_seniors.presenter;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.LoginModel;
import ncu.folder_of_seniors.module.ui.view.LoginView;
import ncu.folder_of_seniors.presenter.impl.LoginPresenterImpl;
import ncu.folder_of_seniors.utils.MsgEnum;
import ncu.folder_of_seniors.utils.Verify;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class LoginPresenter extends BasePresenter<LoginView,LoginModel> implements LoginPresenterImpl {

    @Override
    public String userVerify(String username, String password) {
        if (Verify.isStrEmpty(username) && Verify.isStrEmpty(password)){
            return "输入不能为空！";
        }else if (Verify.isStrEmpty(username)){
            return "请输入登录名！";
        } else if (Verify.isStrEmpty(password)){
            return "请输入密码！";
        } else if (!Verify.isConsistsOfNum_Letter(password)){
            return "请输入合法密码！";
        }else if(Verify.isMobile(username)){
            return "MOBILE";
        }else if(Verify.isEmail(username)){
            return "Email";
        }
        return MsgEnum.SUCCESS+"";
    }

    @Override
    public void login() {
        String username = getView().getUserName();
        String password = getView().getPassword();
        String msg = userVerify(username,password);
        if(!msg.equals("SUCCESS")&&!msg.equals("MOBILE")&&!msg.equals("Email")){
            getView().onLoginFails(msg);
        }else {
            getModel().login(username, password,msg,new BaseLisentener() {
                @Override
                public void onSeccess() {
                    getView().onLoginSeccess("登陆成功！");
                }
                @Override
                public void onFails(String msg) {
                    getView().onLoginFails("登录失败"+msg);
                }
            });
        }
    }

    @Override
    public void logout() {
        BmobUser.logOut();
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
