package ncu.folder_of_seniors.module.ui.view;

import java.util.List;

import ncu.folder_of_seniors.base.BaseView;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public interface PersonalView extends BaseView {

    /**
     * 当数据请求成功，调用此接口提示
     */
    void showUserInfo(User user);
    /**
     * 当数据请求成功，调用此接口提示
     */
    void showFollow(Integer num1,Integer num2,Boolean isFollow);
    /**
     * 当数据请求成功，调用此接口提示
     */
    void showResourceList(List<Resource> resourcesList);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
    /**
     * 当更改关注状态时，调用此接口提示
     */
    void showChangeFollow(Boolean isAdd);
}
