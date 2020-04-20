package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;
import ncu.folder_of_seniors.module.entity.Reviews;

public interface ReviewPresenterImpl extends BasePresenterImpl {

    /**
     * 发布资源
     */
    public void launch(Reviews reviews);
    /**
     * 发布资源
     */
    public void getResource(String resourceId);

}
