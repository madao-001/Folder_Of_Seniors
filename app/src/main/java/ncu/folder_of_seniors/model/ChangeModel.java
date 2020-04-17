package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.FouthFModelImpl;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.User;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class ChangeModel extends BaseModel implements ChangeModelImpl {

    @Override
    public void changeData(String data,String type,RegisterLisentener lisentener) {
        if(type.equals("age")){
            Integer age = Integer.parseInt(data.trim());
            clientUser.setValue(type,age);
        }else {
            clientUser.setValue(type,data);
        }
        clientUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    lisentener.onSeccess( "更改成功");
                }else{
                    lisentener.onFails( "更改失败"+e.getMessage());
                }
            }
        });
    }
}

