package com.ygaps.travelapp.API.Requests;

public class UpdateUserInfoRequest {

    private String fullName;
    private String email;
    private String phone;
    private String dob;
    private int gender;

    public void setFull_name(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFull_name() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }

    public int getGender() {
        return gender;
    }


    public UpdateUserInfoRequest(String fullName, String email, String phone,  String dob, int gender) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
    }
}
