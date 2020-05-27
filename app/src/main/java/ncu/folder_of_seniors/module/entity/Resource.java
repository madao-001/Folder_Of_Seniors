package ncu.folder_of_seniors.module.entity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class Resource extends BmobObject implements Comparable<Resource>{
    private String title;
    private String type;
    private String desc;
    private User creator;
    private Double grade;
    private Integer price;
    private Integer likes;
    private Integer buyNo;
    private Integer starNo;
    private String school;
    private List<String> photos=new ArrayList<>();
    private BmobFile file;
    private Double similarity;//相似度
    private Integer hotpoint;//热度分

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

    public Integer getLikes() {
        return likes;
    }

    public Resource setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getBuyNo() {
        return buyNo;
    }

    public Resource setBuyNo(Integer buyNo) {
        this.buyNo = buyNo;
        return this;
    }

    public Double getGrade() {
        return grade;
    }

    public Resource setGrade(Double grade) {
        this.grade = grade;
        return this;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public Resource setSimilarity(Double similarity) {
        this.similarity = similarity;
        return this;
    }

    public Integer getHotpoint() {
        return hotpoint;
    }

    public Resource setHotpoint(Integer hotpoint) {
        this.hotpoint = hotpoint;
        return this;
    }

    public Integer getStarNo() {
        return starNo;
    }

    public Resource setStarNo(Integer starNo) {
        this.starNo = starNo;
        return this;
    }

    @Override
    public int compareTo(Resource o) {
        return hotpoint > o.getHotpoint() ? -1 : 1;
    }
}
