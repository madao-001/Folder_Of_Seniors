package ncu.folder_of_seniors.module.entity;

import java.lang.reflect.Array;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class User extends BmobUser {
    private Integer age;
    private Boolean sex;
    private String icon;
    private Integer points;
    private String school;
    private String grand;
    private List<String> tags;
    private Boolean isOnline;

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public Integer getAge(){return age;}

    public Boolean isSex() {
        return sex;
    }

    public User setSex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public String getGrand() {
        return grand;
    }

    public User setGrand(String grand) {
        this.grand = grand;
        return this;
    }

    public Boolean isOnline() {
        return isOnline;
    }

    public User setOnline(Boolean online) {
        isOnline = online;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public User setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public User setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public User setSchool(String school) {
        this.school = school;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public User setIcon(String icon) {
        this.icon = icon;
        return this;
    }
}
