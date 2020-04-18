package ncu.folder_of_seniors.presenter.impl;

import android.graphics.Bitmap;

import ncu.folder_of_seniors.base.BasePresenterImpl;

public interface FouthFPresenterImpl extends BasePresenterImpl {

    /**
     * 显示数据
     */
    public void showData();

    /**
     * 显示头像
     */
    public void showIcon();

    /**
     * 上传头像
     */
    public void updateIcon(String picPath);

    /**
     * 保存图片到本地
     */
    public void savePic(Bitmap bitmap);

}
