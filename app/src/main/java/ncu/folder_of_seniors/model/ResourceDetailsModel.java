package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener2;
import ncu.folder_of_seniors.model.impl.FirstFModelImpl;
import ncu.folder_of_seniors.model.impl.RegisterModelImpl;
import ncu.folder_of_seniors.model.impl.ResourceDetailsModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

public class ResourceDetailsModel extends BaseModel implements ResourceDetailsModelImpl {

    @Override
    public void getResource(String objectId, ResouceDetailsLisentener lisentener) {
        BmobQuery<Resource> query = new BmobQuery<Resource>();
        query.addWhereEqualTo("objectId", objectId);
        query.include("creator");
        query.findObjects(new FindListener<Resource>() {
            @Override
            public void done(List<Resource> object, BmobException e) {
                if (e == null) {
                    lisentener.onSeccess(object.get(0));
                }else{
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }

    @Override
    public void getUserAction(Resource resource, User user, ResouceDetailsLisentener2 lisentener) {
        BmobQuery<UserAction> query = new BmobQuery<UserAction>();
        query.addWhereEqualTo("user", user);
        query.addWhereEqualTo("resource", resource);
        query.findObjects(new FindListener<UserAction>() {
            @Override
            public void done(List<UserAction> list, BmobException e) {
                if (e == null) {
                    Boolean isLike=false;
                    Boolean isStar=false;
                    Boolean isBuy=false;
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getActionType().equals("like"))
                            isLike = true;
                        else if (list.get(i).getActionType().equals("star"))
                            isStar = true;
                        else if (list.get(i).getActionType().equals("buy"))
                            isBuy = true;
                    }
                    lisentener.onSeccess(isLike,isStar,isBuy);
                }else{
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }

    @Override
    public void setUserAction(UserAction action, RegisterLisentener lisentener) {
        action.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    lisentener.onSeccess("新增成功:"+objectId);
                } else {
                    Log.e("BMOB", e.toString());
                    lisentener.onFails(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteUserAction(UserAction action, RegisterLisentener lisentener) {
        BmobQuery<UserAction> query = new BmobQuery<UserAction>();
        query.addWhereEqualTo("user", action.getUser());
        query.addWhereEqualTo("resource", action.getResource());
        query.addWhereEqualTo("actionType", action.getActionType());
        query.findObjects(new FindListener<UserAction>() {
            @Override
            public void done(List<UserAction> list, BmobException e) {
                if (e == null) {
                    if(list.get(0)!=null){
                        list.get(0).delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    lisentener.onSeccess("删除"+list.get(0).getObjectId()+"成功");
                                    Log.e("bmob","删除"+list.get(0).getObjectId()+"成功");
                                }else{
                                    Log.e("bmob","删除"+list.get(0).getObjectId()+"失败："+e.getMessage()+","+e.getErrorCode());
                                    lisentener.onFails(e.getMessage());
                                }
                            }
                        });
                    }
                }else{
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }

    @Override
    public void changeLike(Resource resource,Integer num, RegisterLisentener lisentener) {
        resource.increment("likes",num);
        resource.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    lisentener.onSeccess("");
                }else{
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }
}

