package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;

public interface SellEditPresenterImpl extends BasePresenterImpl {

    /**
     * 获取资源信息
     */
    public void getResource(String resourceId);
    /**
     * 更新资源信息
     */
    public void updateResource(Resource resource);
}
