package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener2;
import ncu.folder_of_seniors.model.Lisentener.PersonalLisentener3;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.FirstFModelImpl;
import ncu.folder_of_seniors.model.impl.PersonalModelImpl;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class PersonalModel extends BaseModel implements PersonalModelImpl {
    String follow_objectid;
    Integer num1,num2,likeNo;
    Boolean isFollowing=false;
    User person;
    @Override
    public void findUser(String userid, PersonalLisentener lisentener) {
        BmobQuery<User> test=new BmobQuery<User>();
        test.addWhereEqualTo("objectId",userid);
        test.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> list, BmobException e) {
                if (e==null){
                    if(list.get(0)!=null){
                        lisentener.onSeccess(list.get(0));
                    }else
                        lisentener.onFails("用户好像不存在了哦");
                }
                else {
                    lisentener.onFails("用户好像不存在了哦"+e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }

    @Override
    public void findResourceList(User user, FirstFLisentener lisentener) {
        BmobQuery<Resource> test=new BmobQuery<Resource>();
        test.addWhereEqualTo("creator",user);
        test.include("creator");
        test.findObjects(new FindListener<Resource>() {
            @Override
            public void done(final List<Resource> list, BmobException e) {
                if (e==null){
                    if(list.get(0)!=null){
                        lisentener.onSeccess(list);
                    }else
                        lisentener.onFails("未查到相关发布");
                }
                else {
                    lisentener.onFails("用户好像不存在了哦"+e.getMessage());
                    Log.e("Error:",e.getMessage());
                }
            }
        });
    }

    @Override
    public void findFollow(User user, PersonalLisentener2 lisentener) {
        BmobQuery<Follow> query=new BmobQuery<Follow>();
        query.addWhereEqualTo("userid",user.getObjectId());
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(final List<Follow> list, BmobException e) {
                if (e==null){
                    if(list!=null){
                        follow_objectid=list.get(0).getObjectId();
                        BmobQuery<User> query2 = new BmobQuery<User>();
                        Follow follow=new Follow();
                        follow.setObjectId(follow_objectid);
                        query2.addWhereRelatedTo("following", new BmobPointer(follow));
                        query2.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list1, BmobException e) {
                                if(e==null){
                                    num1 = list1.size();
                                    BmobQuery<User> query3 = new BmobQuery<User>();
                                    Follow follow=new Follow();
                                    follow.setObjectId(follow_objectid);
                                    query3.addWhereRelatedTo("followers", new BmobPointer(follow));
                                    query3.findObjects(new FindListener<User>() {
                                        @Override
                                        public void done(List<User> list2, BmobException e) {
                                            if(e==null){
                                                if(list2!=null){
                                                    num2 = list2.size();
                                                    for(int i = 0;i<list2.size();i++){
                                                        if(list2.get(i).getObjectId().equals(clientUser.getObjectId()))
                                                            isFollowing = true;
                                                    }
                                                    lisentener.onSeccess(num1,num2,isFollowing);
                                                }else
                                                    lisentener.onFails("null");
                                            }else{
                                                lisentener.onFails("查找粉丝出bug了"+e);
                                            }
                                        }
                                    });

                                }else{
                                    lisentener.onFails("查找关注出bug了"+e);
                                }
                            }
                        });
                    }
                }
                else {
                    lisentener.onFails("用户好像不存在了哦"+e);
                }
            }
        });
    }

    @Override
    public void findLikesNo(User user, PersonalLisentener3 lisentener) {
        BmobQuery<Resource> query = new BmobQuery<>();
        query.addWhereEqualTo("creator",user);
        query.findObjects(new FindListener<Resource>() {
            @Override
            public void done(List<Resource> object, BmobException e) {
                if(e==null){
                    likeNo = 0;
                    if(object.size()!=0){
                        for(int i=0;i<object.size();i++){
                            likeNo = likeNo+object.get(i).getLikes();
                        }
                    }
                    lisentener.onSeccess(likeNo);
                }else
                    lisentener.onFails("error:"+e.getMessage());
            }
        });
    }

    @Override
    public void addFollowing(User person,RegisterLisentener lisentener) {
        Follow follow = new Follow();
        BmobQuery<Follow> test=new BmobQuery<>();
        test.addWhereEqualTo("userid",clientUser.getObjectId());
        test.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if(e==null){
                    follow_objectid=list.get(0).getObjectId();
                    follow.setObjectId(follow_objectid);
                    BmobRelation relation = new BmobRelation();
                    relation.add(person);
                    follow.setFollowing(relation);
                    follow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                lisentener.onSeccess("关注成功!");
                            }else {
                                lisentener.onFails("关注失败"+e.getMessage());
                                Log.e("关注失败2",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("关注失败1",e.getMessage());
                }

            }
        });
    }

    @Override
    public void addFollowers(User person,RegisterLisentener lisentener) {
        Follow follow = new Follow();
        String personId = person.getObjectId();
        BmobQuery<Follow> test2=new BmobQuery<>();
        test2.addWhereEqualTo("userid",personId);
        test2.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> object, BmobException e) {
                if(e==null){
                    String follow_objectid2=object.get(0).getObjectId();
                    follow.setObjectId(follow_objectid2);
                    BmobRelation relation = new BmobRelation();
                    relation.add(clientUser);
                    follow.setFollowers(relation);
                    follow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                lisentener.onSeccess("关注成功!");
                            }else {
                                lisentener.onFails("关注失败"+e.getMessage());
                                Log.e("关注失败4",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("关注失败3",e.getMessage());
                }
            }
        });
    }

    @Override
    public void removeFollowing(User person, RegisterLisentener lisentener) {
        Follow follow = new Follow();
        BmobQuery<Follow> test=new BmobQuery<>();
        test.addWhereEqualTo("userid",clientUser.getObjectId());
        test.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> list, BmobException e) {
                if(e==null){
                    follow_objectid=list.get(0).getObjectId();
                    follow.setObjectId(follow_objectid);
                    BmobRelation relation = new BmobRelation();
                    relation.remove(person);
                    follow.setFollowing(relation);
                    follow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                lisentener.onSeccess("取消关注成功!");
                            }else {
                                lisentener.onFails("取消关注失败"+e.getMessage());
                                Log.e("取消关注失败2",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("取消关注失败1",e.getMessage());
                }

            }
        });
    }

    @Override
    public void removeFollowers(User person, RegisterLisentener lisentener) {
        Follow follow = new Follow();
        String personId = person.getObjectId();
        BmobQuery<Follow> test2=new BmobQuery<>();
        test2.addWhereEqualTo("userid",personId);
        test2.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> object, BmobException e) {
                if(e==null){
                    String follow_objectid2=object.get(0).getObjectId();
                    follow.setObjectId(follow_objectid2);
                    BmobRelation relation = new BmobRelation();
                    relation.remove(clientUser);
                    follow.setFollowers(relation);
                    follow.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                lisentener.onSeccess("取消关注成功!");
                            }else {
                                lisentener.onFails("取消关注失败"+e.getMessage());
                                Log.e("取消关注失败4",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("取消关注失败3",e.getMessage());
                }
            }
        });
    }
}

