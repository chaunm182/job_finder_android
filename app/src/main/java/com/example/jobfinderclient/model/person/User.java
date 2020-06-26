
package com.example.jobfinderclient.model.person;

import com.example.jobfinderclient.model.account.Account;
import com.example.jobfinderclient.model.profile.Profile;
import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class User implements Serializable {

    @Expose
    private Address address;
    @Expose
    private FullName fullName;
    @Expose
    private String gender;
    @Expose
    private Long id;
    @Expose
    private String phone;
    @Expose
    private Profile profile;
    @Expose
    private Account account;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
