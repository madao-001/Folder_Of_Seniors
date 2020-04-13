package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;

public class UserAction extends BmobObject {
    private User user;
    private Resource resource;
    private String actionType;
    private Integer grade;

    public String getActionType() {
        return actionType;
    }

    public UserAction setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public Integer getGrade() {
        return grade;
    }

    public UserAction setGrade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserAction setUser(User user) {
        this.user = user;
        return this;
    }

    public Resource getResource() {
        return resource;
    }

    public UserAction setResource(Resource resource) {
        this.resource = resource;
        return this;
    }
}
