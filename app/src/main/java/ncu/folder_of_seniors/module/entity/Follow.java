package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Follow extends BmobObject {
    private String userId;
    private BmobRelation following;
    private BmobRelation followers;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BmobRelation getFollowing() {
        return following;
    }

    public void setFollowing(BmobRelation following) {
        this.following = following;
    }

    public BmobRelation getFollowers() {
        return followers;
    }

    public void setFollowers(BmobRelation followers) {
        this.followers = followers;
    }
}
