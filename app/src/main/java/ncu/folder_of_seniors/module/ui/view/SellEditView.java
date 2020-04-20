package ncu.folder_of_seniors.module.ui.view;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.Resource;

public interface SellEditView extends BaseView {

    /**
     * 获取文件地址
     */
    String getFilePath();
    /**
     * 当数据请求成功，调用此接口提示
     */
    void onLoading();
    /**
     * 当数据请求成功，调用此接口提示
     */
    void showSuccessMessage(String msg);
    /**
     * 当数据请求成功，调用此接口提示
     */
    void showResource(Resource resource);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
}
