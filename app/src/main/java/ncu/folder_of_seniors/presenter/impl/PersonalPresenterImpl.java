package ncu.folder_of_seniors.presenter.impl;

import ncu.folder_of_seniors.base.BasePresenterImpl;
import ncu.folder_of_seniors.module.entity.User;

public interface PersonalPresenterImpl extends BasePresenterImpl {

    /**
     * 获取数据
     */
    public void getData(String userid);

    /**
     * 添加关注
     */
    public void addFollowing(User person);

    /**
     * 取消关注
     */
    public void removeFollowing(User person);
}
