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
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.MyBoughtSelledLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.MyBoughtSelledModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;
import ncu.folder_of_seniors.module.ui.adapter.FirstFAdapter;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class MyBoughtSelledModel extends BaseModel implements MyBoughtSelledModelImpl {
    private int tag = 0;
    private int length = 0;
    @Override
    public void getData(String type, User user, MyBoughtSelledLisentener lisentener) {
        if(!type.equals("")){
            BmobQuery<UserAction> query = new BmobQuery<UserAction>();
            query.include("resource");
            //按照时间降序
            query.order("-createdAt");
            query.addWhereEqualTo("user", user);
            query.addWhereEqualTo("actionType", type);
            query.findObjects(new FindListener<UserAction>() {
                @Override
                public void done(List<UserAction> object, BmobException e) {
                    if(e==null){
                        if(object.size()!=0){
                            lisentener.onSeccess(object);
                        }else
                            lisentener.onFails("还没购买过资源");
                    }else {
                        lisentener.onFails(e.getMessage());
                        Log.e("error", e.getMessage());
                    }
                }
            });
        }else {
            BmobQuery<Resource> query = new BmobQuery<Resource>();
            query.order("-createdAt");
            query.addWhereEqualTo("creator",user);
            query.findObjects(new FindListener<Resource>() {
                @Override
                public void done(List<Resource> list, BmobException e) {
                    if(e==null){
                        if(list.size()!=0){
                            tag = 0;
                            length = list.size();
                            for(int i=0;i<list.size();i++){
                                BmobQuery<UserAction> query2 = new BmobQuery<UserAction>();
                                query2.include("user");
                                query2.order("-createdAt");
                                query2.addWhereEqualTo("resource", list.get(i));
                                query2.addWhereEqualTo("actionType", "buy");
                                query2.findObjects(new FindListener<UserAction>() {
                                    @Override
                                    public void done(List<UserAction> object, BmobException e) {
                                        if(e==null){
                                            if(object.size()!=0){
                                                if(tag==--length){
                                                    for(int j = 0;j<object.size();j++){
                                                        object.get(j).setResource(list.get(tag));
                                                    }
                                                    lisentener.onSeccess(object);
                                                }else{
                                                    for(int j = 0;j<object.size();j++){
                                                        object.get(j).setResource(list.get(tag));
                                                    }
                                                    lisentener.onProgress(object);
                                                }
                                            }
                                        }else {
                                            lisentener.onFails(e.getMessage());
                                            Log.e("error", e.getMessage());
                                        }
                                        tag++;
                                    }
                                });
                            }
                        }else {
                            lisentener.onFails("还没卖出过资源");
                        }
                    }else {
                        lisentener.onFails(e.getMessage());
                    }
                }
            });
        }
    }
}

