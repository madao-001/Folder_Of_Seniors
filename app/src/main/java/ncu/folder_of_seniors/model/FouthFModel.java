package ncu.folder_of_seniors.model;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.BaseLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener2;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.FouthFModelImpl;
import ncu.folder_of_seniors.module.entity.Follow;
import ncu.folder_of_seniors.module.entity.User;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class FouthFModel extends BaseModel implements FouthFModelImpl {

    String follow_objectid;
    User person;
    int num1,num2,num3;
    @Override
    public void showData(FouthFLisentener lisentener) {
        String userid = clientUser.getObjectId();
        num3 = clientUser.getPoints();
        BmobQuery<Follow> query=new BmobQuery<Follow>();
        query.addWhereEqualTo("userid",userid);
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
                                                num2 = list2.size();
                                                lisentener.onSeccess(num1,num2,num3);
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
    public void showIcon(FouthFLisentener2 lisentener) {
        BmobQuery<User> bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("objectId",clientUser.getObjectId());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list,BmobException e) {
                if(e==null){
                    if(list!=null){
                        if(list.get(0).getIcon()!= null){
                            BmobFile bmobfile = list.get(0).getIcon();
                            lisentener.onSeccess(bmobfile);
                        }
                    } else {
                        lisentener.onFails("查询失败："+e.getErrorCode()+","+e.getMessage());
                        Log.e("查询失败：",e.getMessage());
                    }
                }else{
                    Log.e("查询失败：",e.getMessage());
                }
            }
        });
    }

    @Override
    public void addFollowing(RegisterLisentener lisentener) {
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
                    BmobQuery<User> test=new BmobQuery<>();
                    test.addWhereEqualTo("username","madao_001");
                    test.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null){
                                person=list.get(0);
                                relation.add(person);
                                follow.setFollowing(relation);
                                follow.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            lisentener.onSeccess("关注成功!");
                                        }else {
                                            lisentener.onFails("关注失败"+e.getMessage());
                                            Log.e("关注失败3",e.getMessage());
                                        }
                                    }
                                });
                            }else {
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
    public void addFollowers(RegisterLisentener lisentener) {
        Follow follow = new Follow();
        BmobQuery<User> test=new BmobQuery<>();
        test.addWhereEqualTo("username","madao_001");
        test.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    String personId = list.get(0).getObjectId();
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
                                            Log.e("关注失败6",e.getMessage());
                                        }
                                    }
                                });
                            }else {
                                Log.e("关注失败5",e.getMessage());
                            }
                        }
                    });
                }else {
                    Log.e("关注失败4",e.getMessage());
                }
            }
        });
    }

    @Override
    public void downloadFile(BmobFile file, RegisterLisentener lisentener) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {
            @Override
            public void onStart() {
                Log.e("downloadFile:","开始下载");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    lisentener.onSeccess(savePath);
                }else{
                    lisentener.onFails("下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.e("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    @Override
    public void uploadFile(BmobFile file, RegisterLisentener lisentener) {
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.e("success","上传成功");
                    clientUser.setIcon(file);
                    clientUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                lisentener.onSeccess("设置文件到表中成功");
                            } else {
                                lisentener.onFails("设置文件到表中失败"+e.getMessage());
                                Log.e("fail","设置文件到表中失败"+e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.e("fail","上传失败"+e.getMessage());
                }
            }
        });
    }

}

