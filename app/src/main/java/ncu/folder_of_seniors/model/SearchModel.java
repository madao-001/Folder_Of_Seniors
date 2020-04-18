package ncu.folder_of_seniors.model;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.FirstFLisentener;
import ncu.folder_of_seniors.model.impl.FirstFModelImpl;
import ncu.folder_of_seniors.model.impl.SearchModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.utils.Verify;

public class SearchModel extends BaseModel implements SearchModelImpl {

    @Override
    public void showData(String type,String title,FirstFLisentener lisentener) {
        BmobQuery<Resource> query = new BmobQuery<Resource>();
        query.include("creator");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按照时间降序
        query.order("-createdAt");
        switch (type){
            case "search":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                    //query.addWhereEqualTo("type",type);
                    //开启专业版之后可使用模糊搜索
                    //query.addWhereContains("username", title);
                    query.findObjects(new FindListener<Resource>() {
                        @Override
                        public void done(List<Resource> object, BmobException e) {
                            if (e == null) {
                                if(object.size()!=0){
                                    lisentener.onSeccess(object);
                                }else
                                    lisentener.onFails("未查到相关发布");
                            }else{
                                lisentener.onFails(e.getMessage());
                                Log.e("Error:",e.getMessage());
                            }
                        }
                    });
                    break;
                }
                break;
            case "it":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","程序员");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "english":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","英语");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "math":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","数学");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "politics":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","政治");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "life":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","生活");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "other":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("title",title);
                }query.addWhereEqualTo("type","其他");
                //开启专业版之后可使用模糊搜索
                //query.addWhereContains("username", title);
                query.findObjects(new FindListener<Resource>() {
                    @Override
                    public void done(List<Resource> object, BmobException e) {
                        if (e == null) {
                            if(object.size()!=0){
                                lisentener.onSeccess(object);
                            }else
                                lisentener.onFails("未查到相关发布");
                        }else{
                            lisentener.onFails(e.getMessage());
                            Log.e("Error:",e.getMessage());
                        }
                    }
                });
                break;
            case "school":
                if(!Verify.isStrEmpty(title)){
                    query.addWhereEqualTo("school",title);
                    //开启专业版之后可使用模糊搜索
                    //query.addWhereContains("username", title);
                    query.findObjects(new FindListener<Resource>() {
                        @Override
                        public void done(List<Resource> object, BmobException e) {
                            if (e == null) {
                                if(object.size()!=0){
                                    lisentener.onSeccess(object);
                                }else
                                    lisentener.onFails("未查到相关发布");
                            }else{
                                lisentener.onFails(e.getMessage());
                                Log.e("Error:",e.getMessage());
                            }
                        }
                    });
                }
                break;
        }
    }
}

