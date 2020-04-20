package ncu.folder_of_seniors.model;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.impl.LaunchModelImpl;
import ncu.folder_of_seniors.model.impl.ReviewModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;
import ncu.folder_of_seniors.module.entity.UserAction;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class ReviewModel extends BaseModel implements ReviewModelImpl {

    @Override
    public void launchReview(Reviews reviews, List<LocalMedia> list, RegisterLisentener lisentener) {
        if(list!=null&&list.size()!=0){
            String[] filePaths = new String[list.size()];
            for(int i =0;i<list.size();i++){
                filePaths[i]=list.get(i).getPath();
            }
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if(list1.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                        //do something
                        reviews.setPhotos(list1);
                        //上传review表
                        reviews.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    //更新UserAction表，先查询objectId
                                    BmobQuery<UserAction> query = new BmobQuery<>();
                                    query.addWhereEqualTo("user",reviews.getUser());
                                    query.addWhereEqualTo("resource",reviews.getResource());
                                    query.addWhereEqualTo("actionType","buy");
                                    query.findObjects(new FindListener<UserAction>() {
                                        @Override
                                        public void done(List<UserAction> object, BmobException e) {
                                            if(e==null){
                                                if(object==null){
                                                    Log.e("error","useraction查询失败");
                                                }else {
                                                    object.get(0).setGrade(reviews.getGrade());
                                                    object.get(0).update(object.get(0).getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if(e==null){
                                                                //更新Resource表中的评分
                                                                Resource resource = reviews.getResource();
                                                                BmobQuery<Reviews> query1 = new BmobQuery<>();
                                                                query1.addWhereEqualTo("resource",resource);
                                                                query1.findObjects(new FindListener<Reviews>() {
                                                                    @Override
                                                                    public void done(List<Reviews> object, BmobException e) {
                                                                        if(e==null){
                                                                            if(object!=null){
                                                                                int sum=0;
                                                                                for(int i=0;i<object.size();i++){
                                                                                    sum = sum+object.get(i).getGrade();
                                                                                }
                                                                                Integer grade = reviews.getGrade();
                                                                                Integer a = sum+grade;
                                                                                Double b = object.size()+1.0;
                                                                                Double score = a/b;
                                                                                resource.setGrade(score);
                                                                                resource.update(resource.getObjectId(), new UpdateListener() {
                                                                                    @Override
                                                                                    public void done(BmobException e) {
                                                                                        if(e==null){
                                                                                            lisentener.onSeccess("评论发布成功！");
                                                                                        }else
                                                                                            lisentener.onFails(e.getMessage());
                                                                                    }
                                                                                });
                                                                            }
                                                                        }else {
                                                                            lisentener.onFails(e.getMessage());
                                                                            Log.e("error4",e.getMessage());
                                                                        }
                                                                    }
                                                                });
                                                            }else {
                                                                lisentener.onFails(e.getMessage());
                                                                Log.e("error3",e.getMessage());
                                                            }
                                                        }
                                                    });
                                                }
                                            }else{
                                                lisentener.onFails(e.getMessage());
                                                Log.e("error2",e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    lisentener.onFails("评论发布失败~~~~"+e.getMessage());
                                    Log.e("error1","评论发布失败~~~~"+e.getMessage());
                                }
                            }
                        });
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                    //Toast.makeText(LaunchActivity.this,"图片正在上传请稍等",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int i, String s) {
                    lisentener.onFails(i+s);
                }
            });
        }else {
            //上传review表
            reviews.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        //更新UserAction表，先查询objectId
                        BmobQuery<UserAction> query = new BmobQuery<>();
                        query.addWhereEqualTo("user",reviews.getUser());
                        query.addWhereEqualTo("resource",reviews.getResource());
                        query.addWhereEqualTo("actionType","buy");
                        query.findObjects(new FindListener<UserAction>() {
                            @Override
                            public void done(List<UserAction> object, BmobException e) {
                                if(e==null){
                                    if(object==null){
                                        Log.e("error","useraction查询失败");
                                    }else {
                                        object.get(0).setGrade(reviews.getGrade());
                                        object.get(0).update(object.get(0).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    //更新Resource表中的评分
                                                    Resource resource = reviews.getResource();
                                                    BmobQuery<Reviews> query1 = new BmobQuery<>();
                                                    query1.addWhereEqualTo("resource",resource);
                                                    query1.findObjects(new FindListener<Reviews>() {
                                                        @Override
                                                        public void done(List<Reviews> object, BmobException e) {
                                                            if(e==null){
                                                                if(object!=null){
                                                                    int sum=0;
                                                                    for(int i=0;i<object.size();i++){
                                                                        sum = sum+object.get(i).getGrade();
                                                                    }
                                                                    Double grade = resource.getGrade();
                                                                    Double a = sum+grade;
                                                                    Double b = object.size()+1.0;
                                                                    Double score = a/b;
                                                                    resource.setGrade(score);
                                                                    resource.update(resource.getObjectId(), new UpdateListener() {
                                                                        @Override
                                                                        public void done(BmobException e) {
                                                                            if(e==null){
                                                                                lisentener.onSeccess("评论发布成功！");
                                                                            }else
                                                                                lisentener.onFails(e.getMessage());
                                                                        }
                                                                    });
                                                                }
                                                            }else {
                                                                lisentener.onFails(e.getMessage());
                                                                Log.e("error4",e.getMessage());
                                                            }
                                                        }
                                                    });
                                                }else {
                                                    lisentener.onFails(e.getMessage());
                                                    Log.e("error3",e.getMessage());
                                                }
                                            }
                                        });
                                    }
                                }else{
                                    lisentener.onFails(e.getMessage());
                                    Log.e("error2",e.getMessage());
                                }
                            }
                        });
                    } else {
                        lisentener.onFails("评论发布失败~~~~"+e.getMessage());
                        Log.e("error1","评论发布失败~~~~"+e.getMessage());
                    }
                }
            });
        }

    }

    @Override
    public void getResource(String resourceId, ResouceDetailsLisentener lisentener) {
        BmobQuery<Resource> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",resourceId);
        query.findObjects(new FindListener<Resource>() {
            @Override
            public void done(List<Resource> object, BmobException e) {
                if(e==null){
                    if(object.get(0)!=null){
                        Resource resource = object.get(0);
                        lisentener.onSeccess(resource);
                    }else
                        lisentener.onFails("未查到该资源！");
                }else
                    lisentener.onFails("error:"+e.getMessage());
            }
        });
    }
}

