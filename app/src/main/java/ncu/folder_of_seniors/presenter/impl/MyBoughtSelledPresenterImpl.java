package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.User;

public interface MyBoughtSelledPresenterImpl extends BasePresenterImpl {

    /**
     * 获取数据
     */
    public void getData(String type, User user);

}
