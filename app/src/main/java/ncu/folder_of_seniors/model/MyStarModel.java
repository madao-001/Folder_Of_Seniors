package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.MyStarModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class MyStarModel extends BaseModel implements MyStarModelImpl {

    @Override
    public void getData(User user, FirstFLisentener lisentener) {
        BmobQuery<UserAction> query = new BmobQuery<UserAction>();
        query.include("resource.creator[username|icon]");
        //按照时间降序
        query.order("-createdAt");
        query.addWhereEqualTo("user", user);
        query.addWhereEqualTo("actionType", "star");
        query.findObjects(new FindListener<UserAction>() {
            @Override
            public void done(List<UserAction> object, BmobException e) {
                if(e==null){
                    if(object.size()!=0){
                        List<Resource> resourceList = new ArrayList<>();
                        for(int i=0;i<object.size();i++){
                            Resource resource = object.get(i).getResource();
                            resourceList.add(resource);
                        }
                        lisentener.onSeccess(resourceList);
                    }else
                        lisentener.onFails("还没收藏过资源");
                }else {
                    lisentener.onFails(e.getMessage());
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    @Override
    public void removeStar(Resource resource, User user, RegisterLisentener lisentener) {
        BmobQuery<UserAction> query = new BmobQuery<UserAction>();
        query.addWhereEqualTo("resource", resource);
        query.addWhereEqualTo("user", user);
        query.addWhereEqualTo("actionType", "star");
        query.findObjects(new FindListener<UserAction>() {
            @Override
            public void done(List<UserAction> object, BmobException e) {
                if(e==null){
                    if(object.size()!=0){
                        UserAction userAction = object.get(0);
                        userAction.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null)
                                    lisentener.onSeccess("取消收藏成功！");
                                else
                                    lisentener.onFails(e.getMessage());
                            }
                        });
                    }
                }else {
                    lisentener.onFails(e.getMessage());
                    Log.e("error", e.getMessage());
                }
            }
        });
    }
}

