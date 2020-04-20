package ncu.folder_of_seniors.model;

import android.util.Log;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.Lisentener.ResouceDetailsLisentener;
import ncu.folder_of_seniors.model.impl.LaunchModelImpl;
import ncu.folder_of_seniors.model.impl.SearchModelImpl;
import ncu.folder_of_seniors.model.impl.SellEditModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;

public class SellEditModel extends BaseModel implements SellEditModelImpl {

    private BmobFile file;
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

    @Override
    public void udpateResource(Resource resource,String filePath,RegisterLisentener lisentener) {
        file = resource.getFile();
        if(filePath.equals(resource.getFile().getFileUrl())){
            resource.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        lisentener.onSeccess("资源信息修改成功！");
                    } else {
                        lisentener.onFails("资源修改失败~~~~"+e.getMessage());
                        Log.e("error","资源修改失败~~~~"+e.getMessage());
                    }
                }
            });
        }else {
            BmobFile bmobFile = new BmobFile(new File(filePath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        resource.setValue("file",bmobFile);
                        resource.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    file.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                                lisentener.onSeccess("资源信息修改成功！");
                                            }else{
                                                lisentener.onFails("文件删除失败~~~~"+e.getMessage());
                                            }
                                        }
                                    });
                                } else {
                                    lisentener.onFails("资源修改失败~~~~"+e.getMessage());
                                    Log.e("error","资源修改失败~~~~"+e.getMessage());
                                }
                            }
                        });
                    }else{
                        lisentener.onFails(e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
    }
}

