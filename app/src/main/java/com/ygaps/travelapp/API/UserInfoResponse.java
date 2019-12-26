package com.ygaps.travelapp.API;

public class UserInfoResponse {
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private int gender;
    private boolean email_verified;
    private boolean phone_verified;

    public void setId(int id) {
        this.id = id;
    }

    public void setFull_name(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public void setPhone_verified(boolean phone_verified) {
        this.phone_verified = phone_verified;
    }

    public int getId() {
        return id;
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

    public String getAddress() {
        return address;
    }

    public String getDob() {
        return dob;
    }

    public int getGender() {
        return gender;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public boolean isPhone_verified() {
        return phone_verified;
    }

    public UserInfoResponse(int id, String fullName, String email, String phone, String address, String dob, int gender, boolean email_verified, boolean phone_verified) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.email_verified = email_verified;
        this.phone_verified = phone_verified;
    }
}
