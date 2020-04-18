package ncu.folder_of_seniors.module.ui.view;

import java.util.List;

import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.Resource;

public interface ResourceDetailsView extends BaseView {

    /**
     * 当数据请求成功，调用此接口提示
     */
    void showData(Resource resource,Boolean isLike, Boolean isStar, Boolean isBuy);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
    /**
     * 当操作成功时，返回结果改变界面
     */
    void showActionResult(String type,Boolean isAdd);
}
