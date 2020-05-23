package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.impl.FirstFModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;
import ncu.folder_of_seniors.module.entity.User;
import ncu.folder_of_seniors.module.entity.UserAction;

import static ncu.folder_of_seniors.app.MyApplication.resources;
import static ncu.folder_of_seniors.app.MyApplication.reviews;
import static ncu.folder_of_seniors.app.MyApplication.userActions;
import static ncu.folder_of_seniors.app.MyApplication.users;

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

    @Override
    public void showAllData(BaseLisentener lisentener) {
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        Log.e("MyApplication:","开始查询所有用户");
        userBmobQuery.order("-createdAt")
                .findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if (e == null) {
                            users.clear();
                            users.addAll(object);
                            Log.e("MyApplication:","查询所有用户成功"+users.size());
                            lisentener.onSeccess();
                        } else {
                            lisentener.onFails("查询所有用户数据失败！");
                        }
                    }
                });
        BmobQuery<Resource> resourceBmobQuery = new BmobQuery<>();
        Log.e("MyApplication:","开始查询所有资源");
        resourceBmobQuery.include("creator");
        resourceBmobQuery.order("-likes")
                .findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            resources.clear();
                            resources.addAll(object);
                            Log.e("MyApplication:","查询所有资源成功"+resources.size());
                            lisentener.onSeccess();
                        } else {
                            lisentener.onFails("查询所有资源数据失败！");
                        }
                    }
                });
        BmobQuery<UserAction> userActionBmobQuery = new BmobQuery<>();
        Log.e("MyApplication:","开始查询所有用户行为");
        userActionBmobQuery.order("-createdAt")
                .findObjects(new FindListener<UserAction>() {
                    @Override
                    public void done(List<UserAction> object, BmobException e) {
                        if (e == null) {
                            userActions.clear();
                            userActions.addAll(object);
                            Log.e("MyApplication:","查询所有用户行为成功"+userActions.size());
                            lisentener.onSeccess();
                        } else {
                            lisentener.onFails("查询所有用户行为数据失败！");
                        }
                    }
                });
        BmobQuery<Reviews> reviewsBmobQuery = new BmobQuery<>();
        Log.e("MyApplication:","开始查询所有资源评价");
        reviewsBmobQuery.order("-createdAt")
                .findObjects(new FindListener<Reviews>() {
                    @Override
                    public void done(List<Reviews> object, BmobException e) {
                        if (e == null) {
                            reviews.clear();
                            reviews.addAll(object);
                            Log.e("MyApplication:","查询所有资源评价成功"+reviews.size());
                            lisentener.onSeccess();
                        } else {
                            lisentener.onFails("查询所有资源评价失败！");
                        }
                    }
                });
    }
}

