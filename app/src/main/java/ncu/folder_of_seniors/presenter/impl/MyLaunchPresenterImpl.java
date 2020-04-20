package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;

public interface MyLaunchPresenterImpl extends BasePresenterImpl {

    /**
     * 显示数据
     */
    public void showData();

    /**
     * 删除发布
     */
    public void deleteData(Resource resource);
}
