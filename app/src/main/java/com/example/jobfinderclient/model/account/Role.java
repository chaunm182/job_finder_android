
package com.example.jobfinderclient.model.account;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Role implements Serializable {

    @Expose
    private Long id;
    @Expose
    private String name;

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

}
