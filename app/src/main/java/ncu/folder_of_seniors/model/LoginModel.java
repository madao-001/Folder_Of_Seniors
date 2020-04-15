package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.impl.LoginModelImpl;
import ncu.folder_of_seniors.module.entity.User;

public class LoginModel extends BaseModel implements LoginModelImpl {

    @Override
    public void login(String username, String password,String type,BaseLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        User userlogin = new User();
        switch (type){
            case "MOBILE":
                userlogin.setMobilePhoneNumber(username);
                userlogin.setPassword(password);
                //查询
                BmobQuery<User> test = new BmobQuery<>();
                test.addWhereEqualTo("mobilePhoneNumber", username);
                test.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(list!=null){
                            if (list.get(0).isOnline()) {
                                lisentener.onFails("该账号已登录");
                            }else {
                                BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        if(user!=null&&e==null){
                                            user.setOnline(true);
                                            user.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e==null){
                                                        lisentener.onSeccess();
                                                    }
                                                }
                                            });
                                        }else {
                                            lisentener.onFails(e.getMessage());
                                            Log.e("error", e.getMessage());
                                        }
                                    }
                                });
                            }
                        }else {
                            lisentener.onFails(e.getMessage());
                        }
                    }
                });
                break;
            case "Email":
                userlogin.setEmail(username);
                userlogin.setPassword(password);
                //查询
                BmobQuery<User> test2 = new BmobQuery<>();
                test2.addWhereEqualTo("email", username);
                test2.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(list!=null){
                            if (list.get(0).isOnline()) {
                                lisentener.onFails("该账号已登录");
                            }else {
                                BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        if(user!=null&&e==null){
                                            user.setOnline(true);
                                            user.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e==null){
                                                        lisentener.onSeccess();
                                                    }
                                                }
                                            });
                                        }else {
                                            lisentener.onFails(e.getMessage());
                                            Log.e("error", e.getMessage());
                                        }
                                    }
                                });
                            }
                        }else {
                            lisentener.onFails(e.getMessage());
                        }
                    }
                });
                break;
            case "SUCCESS":
                userlogin.setUsername(username);
                userlogin.setPassword(password);
                //查询
                BmobQuery<User> test3 = new BmobQuery<>();
                test3.addWhereEqualTo("username", username);
                test3.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if(list!=null){
                            if (list.get(0).isOnline()) {
                                lisentener.onFails("该账号已登录");
                            }else {
                                BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        if(user!=null&&e==null){
                                            user.setOnline(true);
                                            user.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e==null){
                                                        lisentener.onSeccess();
                                                    }
                                                }
                                            });
                                        }else {
                                            lisentener.onFails(e.getMessage());
                                            Log.e("error", e.getMessage());
                                        }
                                    }
                                });
                            }
                        }else {
                            lisentener.onFails(e.getMessage());
                        }
                    }
                });
                break;
        }
    }
}

