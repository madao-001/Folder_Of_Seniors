package ncu.folder_of_seniors.module.ui.view;

import java.util.List;

import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.UserAction;

public interface MyBoughtSelledView extends BaseView {

    /**
     * 当数据请求成功，调用此接口提示
     */
    void showData(List<UserAction> userActionList);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
}
