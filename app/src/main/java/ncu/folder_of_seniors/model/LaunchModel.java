package ncu.folder_of_seniors.model;

import android.util.Log;
import android.widget.Toast;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import ncu.folder_of_seniors.base.BaseModel;
import ncu.folder_of_seniors.model.Lisentener.LaunchLisentener;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.model.impl.ChangeModelImpl;
import ncu.folder_of_seniors.model.impl.LaunchModelImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.ui.LaunchActivity;

import static ncu.folder_of_seniors.app.MyApplication.clientUser;

public class LaunchModel extends BaseModel implements LaunchModelImpl {


    @Override
    public void launchResource(Resource resource,String[] filePaths, RegisterLisentener lisentener) {
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                    //2、urls-上传文件的完整url地址
                    if(list1.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                        //do something
                        resource.setFile(list.get(0));
                        resource.setPhotos(list1);
                        resource.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    lisentener.onSeccess("资源发布成功！");
                                } else {
                                    lisentener.onFails("资源发布失败~~~~"+e.getMessage());
                                    Log.e("error","资源发布失败~~~~"+e.getMessage());
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
        }

//    @Override
//    public void uploadFile(BmobFile file, LaunchLisentener lisentener) {
//        file.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Log.e("success","上传成功");
//                    lisentener.onSeccess(file);
//                } else {
//                    lisentener.onFails("上传失败"+e.getMessage());
//                    Log.e("fail","上传失败"+e.getMessage());
//                }
//            }
//        });
//    }

}

