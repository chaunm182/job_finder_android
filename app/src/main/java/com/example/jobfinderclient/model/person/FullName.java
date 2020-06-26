
package com.example.jobfinderclient.model.person;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class FullName implements Serializable {

    @Expose
    private String firstName;
    @Expose
    private Long id;
    @Expose
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
