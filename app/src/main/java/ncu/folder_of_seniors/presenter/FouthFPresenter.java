package ncu.folder_of_seniors.presenter;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import ncu.folder_of_seniors.base.BasePresenter;
import ncu.folder_of_seniors.model.FouthFModel;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener;
import ncu.folder_of_seniors.model.Lisentener.FouthFLisentener2;
import ncu.folder_of_seniors.model.Lisentener.RegisterLisentener;
import ncu.folder_of_seniors.module.ui.view.FouthFView;
import ncu.folder_of_seniors.presenter.impl.FouthFPresenterImpl;
import ncu.folder_of_seniors.utils.MsgEnum;
import ncu.folder_of_seniors.utils.Verify;

/**
 * presenter 层，承担业务逻辑处理，数据源处理等
 */
public class FouthFPresenter extends BasePresenter<FouthFView, FouthFModel> implements FouthFPresenterImpl {
    FileOutputStream out;
    String bitmapName = "icon.jpg";
    String FilePath;

    @Override
    public void showData() {
        getModel().showData(new FouthFLisentener() {
            @Override
            public void onSeccess(int num1, int num2,int num3) {
                getView().showData(num1,num2,num3);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void showIcon() {
        getModel().showIcon(new FouthFLisentener2() {
            @Override
            public void onSeccess(String iconURL) {
                getView().showIcon(iconURL);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void updateIcon(String picPath) {
        BmobFile bmobFile = new BmobFile(new File(picPath));
        getModel().uploadFile(bmobFile, new RegisterLisentener() {
            @Override
            public void onSeccess(String msg) {
                Log.e("success",msg);
            }

            @Override
            public void onFails(String msg) {
                getView().showErrorMessage(msg);
            }
        });
    }

    @Override
    public void savePic(Bitmap bitmap) {
        try { // 获取SDCard指定目录下
            String sdCardDir = Environment.getExternalStorageDirectory() + "/Folder_Of_Seniors/";
            Log.e("savePic","start2");
            File dirFile = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile.exists()) {              //如果不存在，那就建立这个文件夹
                dirFile.mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, bitmapName);// 在SDcard的目录下创建图片文,以当前时间为其命名
            Log.e("savePic","start3");
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            System.out.println("_________保存到____sd______指定目录文件夹下____________________");
            Log.e("saveBitMap", "saveBitmap: 图片保存到" + Environment.getExternalStorageDirectory() + "/Folder_Of_Seniors/" + bitmapName);
            FilePath = Environment.getExternalStorageDirectory() + "/Folder_Of_Seniors/" + bitmapName;
            getView().getPicPath(FilePath);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detach() {
        super.detach();
        /**
         * 释放内存、关闭网络请求、关闭线程等操作
         */
        Log.e("==========", "detach: 解除绑定，释放内存");

    }
}
