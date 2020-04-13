package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Follow extends BmobObject {
    private User user;
    private BmobRelation following;
    private BmobRelation followers;


    public BmobRelation getFollowing() {
        return following;
    }

    public Follow setFollowing(BmobRelation following) {
        this.following = following;
        return this;
    }

    public BmobRelation getFollowers() {
        return followers;
    }

    public Follow setFollowers(BmobRelation followers) {
        this.followers = followers;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Follow setUser(User user) {
        this.user = user;
        return this;
    }
}
