package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.Resource;

public interface MyStarPresenterImpl extends BasePresenterImpl {

    /**
     * 显示数据
     */
    public void showData();

    /**
     * 取消收藏
     */
    public void removeStar(Resource resource);
}
