package ncu.folder_of_seniors.model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.SettingModelImpl;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class SettingModel extends BaseModel implements SettingModelImpl {

    @Override
    public void logOut(RegisterLisentener lisentener) {
        clientUser.setValue("isOnline",false);
        clientUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    clientUser.logOut();
                    lisentener.onSeccess( "退出登录");
                }else{
                    lisentener.onFails( "登陆状态更改失败"+e.getMessage());
                }
            }
        });
    }
}

