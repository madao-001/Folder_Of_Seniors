package ncu.folder_of_seniors.module.ui.view;

import java.util.List;

import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.Resource;

public interface SearchView extends BaseView {

    /**
     * 获取类别
     */
    String getType();
    /**
     * 获取搜索关键字
     */
    String getKeyWord();
    /**
     * 当数据请求成功，调用此接口返回给前端
     */
    void showData(List<Resource> list);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
}
