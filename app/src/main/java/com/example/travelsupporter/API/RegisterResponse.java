package com.example.travelsupporter.API;

public class RegisterResponse {
    private int id;
    private String username;
    private String full_name;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getUsername() {
        return username;
    }

    public String getFull_name() {
        return full_name;
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

    public RegisterResponse(int id, String username, String full_name, String email, String phone, String address, String dob, int gender, boolean email_verified, boolean phone_verified) {
        this.id = id;
        this.username = username;
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.email_verified = email_verified;
        this.phone_verified = phone_verified;
    }
}
