package ncu.folder_of_seniors.module.entity;

import cn.bmob.v3.BmobObject;

public class Deal extends BmobObject {
    private Resource resource;
    private User buyer;
    private User seller;
    private Integer points;
    private Boolean isCompleted;


    public Integer getPoints() {
        return points;
    }

    public Deal setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public Deal setCompleted(Boolean completed) {
        this.isCompleted = completed;
        return this;
    }

    public Resource getResource() {
        return resource;
    }

    public Deal setResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public User getBuyer() {
        return buyer;
    }

    public Deal setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public User getSeller() {
        return seller;
    }

    public Deal setSeller(User seller) {
        this.seller = seller;
        return this;
    }
}
