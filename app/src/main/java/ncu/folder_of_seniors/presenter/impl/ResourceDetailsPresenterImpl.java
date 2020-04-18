package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.User;

public interface ResourceDetailsPresenterImpl extends BasePresenterImpl {

    /**
     * 获取数据
     */
    public void getData(String resourceId);

    /**
     * 添加行为
     */
    public void setUserAction(Resource resource, User user,String type,Boolean isAdd);

}
