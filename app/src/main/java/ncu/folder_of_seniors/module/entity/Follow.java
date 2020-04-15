package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Follow extends BmobObject {
    private String userid;
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

    public String getUserid() {
        return userid;
    }

    public Follow setUserid(String userid) {
        this.userid = userid;
        return this;
    }
}
