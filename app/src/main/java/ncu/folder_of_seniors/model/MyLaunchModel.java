package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.MyLaunchModelImpl;
import ncu.folder_of_seniors.model.impl.MyStarModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

public class MyLaunchModel extends BaseModel implements MyLaunchModelImpl {

    @Override
    public void getData(User user, FirstFLisentener lisentener) {
        BmobQuery<Resource> query = new BmobQuery<Resource>();
        //按照时间降序
        query.order("-createdAt");
        query.addWhereEqualTo("creator", user);
        query.findObjects(new FindListener<Resource>() {
            @Override
            public void done(List<Resource> object, BmobException e) {
                if(e==null){
                    if(object.size()!=0){
                        lisentener.onSeccess(object);
                    }else
                        lisentener.onFails("还没发布过资源");
                }else {
                    lisentener.onFails(e.getMessage());
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteData(Resource resource,RegisterLisentener lisentener) {
        resource.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    lisentener.onSeccess("删除发布成功！");
                }else {
                    lisentener.onFails("删除发布失败："+e.getMessage());
                }
            }
        });
    }
}

