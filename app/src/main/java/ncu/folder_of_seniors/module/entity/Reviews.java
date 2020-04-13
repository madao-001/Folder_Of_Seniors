package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;

public class Reviews extends BmobObject {
    private Resource resource;
    private User user;
    private Integer grade;
    private String desc;

    public Integer getGrade() {
        return grade;
    }

    public Reviews setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Reviews setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Resource getResource() {
        return resource;
    }

    public Reviews setResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reviews setUser(User user) {
        this.user = user;
        return this;
    }
}
