package com.androidtutorialshub.loginregister.model;


public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String parentmobile;

    public String getParentmobile() {
        return parentmobile;
    }

    public void setParentmobile(String parentmobile) {
        this.parentmobile = parentmobile;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
