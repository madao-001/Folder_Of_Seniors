package ncu.folder_of_seniors.module.entity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class Resource extends BmobObject {
    private String title;
    private String type;
    private String desc;
    private User creator;
    private Integer score;
    private Integer price;
    private String school;
    private List<String> photos=new ArrayList<>();
    private BmobFile file;

    public String getTitle() {
        return title;
    }

    public Resource setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getType() {
        return type;
    }

    public Resource setType(String type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Resource setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public Resource setScore(Integer score) {
        this.score = score;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Resource setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Resource setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public BmobFile getFile() {
        return file;
    }

    public Resource setFile(BmobFile file) {
        this.file = file;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public Resource setSchool(String school) {
        this.school = school;
        return this;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public Resource setPhotos(List<String> photos) {
        this.photos = photos;
        return this;
    }
}
