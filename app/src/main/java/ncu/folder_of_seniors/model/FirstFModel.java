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
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.impl.FirstFModelImpl;
import ncu.folder_of_seniors.model.impl.LoginModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public class FirstFModel extends BaseModel implements FirstFModelImpl {

    @Override
    public void showData(FirstFLisentener lisentener) {
        BmobQuery<Resource> query = new BmobQuery<Resource>();
        query.include("creator");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Resource>() {
            @Override
            public void done(List<Resource> object, BmobException e) {
                if (e == null) {
                    lisentener.onSeccess(object);
                }else{
                    lisentener.onFails(e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }
}

