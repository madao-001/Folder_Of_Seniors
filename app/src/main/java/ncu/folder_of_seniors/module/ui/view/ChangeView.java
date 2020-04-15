package ncu.folder_of_seniors.module.ui.view;

import ncu.folder_of_seniors.base.BaseView;

public interface ChangeView extends BaseView {

    /**
     * 当数据请求成功，调用此接口提示
     */
    void showSuccessMessage(String msg);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
}
