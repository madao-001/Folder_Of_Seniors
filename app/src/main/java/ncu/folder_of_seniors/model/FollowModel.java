package ncu.folder_of_seniors.model;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FollowLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.FollowModelImpl;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.User;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class FollowModel extends BaseModel implements FollowModelImpl {

    @Override
    public void getFollow(String type, FollowLisentener lisentener) {
        String userid = clientUser.getObjectId();
        BmobQuery<Follow> query=new BmobQuery<Follow>();
        query.addWhereEqualTo("userid",userid);
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if (e==null) {
                    if (list!= null) {
                        String follow_objectid=list.get(0).getObjectId();
                        BmobQuery<User> query2 = new BmobQuery<User>();
                        Follow follow=new Follow();
                        follow.setObjectId(follow_objectid);
                        query2.addWhereRelatedTo(type, new BmobPointer(follow));
                        query2.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> object, BmobException e) {
                                if(e==null){
                                    if(object!=null){
                                        lisentener.onSeccess(object);
                                    }else{
                                        if(type.equals("followers")){
                                            lisentener.onFails("还没有人关注你...");
                                        }else {
                                            lisentener.onFails("还没有关注他人...");
                                        }
                                    }
                                }else
                                    lisentener.onFails(e.getMessage());
                            }
                        });
                    }
                }else
                    lisentener.onFails(e.getMessage());
            }
        });
    }
}

