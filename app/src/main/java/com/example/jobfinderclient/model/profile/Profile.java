
package com.example.jobfinderclient.model.profile;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Profile implements Serializable {

    @Expose
    private String avatar;
    @Expose
    private String description;
    @Expose
    private Long id;
    @Expose
    private String position;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
