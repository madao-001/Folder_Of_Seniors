package ncu.folder_of_seniors.model;

import android.util.Log;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.LoginModelImpl;
import ncu.folder_of_seniors.model.impl.RegisterModelImpl;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.User;

public class RegisterModel extends BaseModel implements RegisterModelImpl {

    @Override
    public void registerByEmail(BmobUser user, RegisterLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null && e == null) {
                    BmobUser.requestEmailVerify(user.getEmail(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Follow follow = new Follow();
                                follow.setUserid(user.getObjectId());
                                follow.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        //Toast.makeText(getApplicationContext(), "fansfocus新增成功", Toast.LENGTH_SHORT).show();
                                        lisentener.onSeccess("请求验证邮件成功，请到" + user.getEmail() + "邮箱中进行激活。");
                                    }
                                });
                            }else{
                                lisentener.onFails(e.getMessage());
                                Log.e("error", e.getMessage());
                            }
                        }
                    });
                } else {
                    lisentener.onFails(e.getMessage());
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    @Override
    public void registerByPhone(BmobUser user, String smsCode, RegisterLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        user.signUp(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    //用户注册成功时，在关注表中增添一行数据
                    Follow follow = new Follow();
                    follow.setUserid(user.getObjectId());
                    follow.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            //Toast.makeText(getApplicationContext(), "fansfocus新增成功", Toast.LENGTH_SHORT).show();
                            lisentener.onSeccess("注册成功！");
                        }
                    });
                }else{
                    lisentener.onFails("注册失败:"+e.getMessage());
                }
            }
        });
    }

    @Override
    public void requestSMSCode(String phoneNo, RegisterLisentener lisentener) {
        BmobSMS.requestSMSCode(phoneNo, "学长的文件夹", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    lisentener.onSeccess("发送验证码成功，短信ID：" + smsId + "\n");
                } else {
                    lisentener.onFails("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }

    @Override
    public void verifySmsCode(String phoneNo, String smsCode, BaseLisentener lisentener) {
        BmobSMS.verifySmsCode(phoneNo, smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    lisentener.onSeccess();
                } else {
                    lisentener.onFails("验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }
}

