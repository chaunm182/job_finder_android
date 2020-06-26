
package com.example.jobfinderclient.model.post;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class JobCategory implements Serializable {

    @Expose
    private Long id;
    @Expose
    private String name;

    public JobCategory() {
    }

    public JobCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
