package com.example.jobfinderclient.model.post;

import com.example.jobfinderclient.model.person.User;
import com.example.jobfinderclient.util.DateDeserializer;
import com.google.gson.annotations.JsonAdapter;

import java.util.Date;

public class RecruitmentDetail {

    private Integer id;

    @JsonAdapter(DateDeserializer.class)
    private Date applyAt;
    private String status;
    private Post post;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getApplyAt() {
        return applyAt;
    }

    public void setApplyAt(Date applyAt) {
        this.applyAt = applyAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
