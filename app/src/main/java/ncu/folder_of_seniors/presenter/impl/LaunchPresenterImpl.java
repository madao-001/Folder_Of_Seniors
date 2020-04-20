package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;

public interface LaunchPresenterImpl extends BasePresenterImpl {

    /**
     * 发布资源
     */
    public void launch(Resource resource);

}
