
package com.example.jobfinderclient.model.person;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class Address implements Serializable {

    @Expose
    private String addressDetail;
    @Expose
    private String city;
    @Expose
    private String district;
    @Expose
    private Long id;
    @Expose
    private String street;

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
