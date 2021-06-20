package com.example.mediraj.model.checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckoutModel {
    private Integer user;
    private String name;
    private String mobile;
    private String address;
    private List<Checkout> checkouts = null;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<Checkout> checkouts) {
        this.checkouts = checkouts;
    }
}
