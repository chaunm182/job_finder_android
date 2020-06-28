
package com.example.jobfinderclient.model.profile;

public class Skill { //kỹ năng
    private Integer id; //id
    private String description;//mô tả về kỹ năng
    private String name;//tên kỹ năng
    private Double point;//điểm tự đánh giá, trên thang điểm 1->5
    private Profile profile;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
