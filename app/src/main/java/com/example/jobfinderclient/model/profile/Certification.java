package com.example.jobfinderclient.model.profile;

import java.util.Date;

public class Certification {
    private Integer id;//id
    private String name;//tên chứng chỉ
    private String organization;//tên tổ chức cấp chứng chỉ
    private Date receiveTime;//ngày nhận chứng chỉ
    private Profile profile;

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
