package com.example.travelsupporter.API;

public class RegisterRequest {
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String dob;
    private String gender;

    public RegisterRequest(String password, String fullName, String email, String phone, String address, String dob, String gender )
    {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFullName(String fullName) {
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
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String setPassword() {
        return password;
    }
    public String setFullName() { return fullName; }
    public String setEmail() { return email; }
    public String setPhone() {
        return phone;
    }
    public String setAddress() {
        return address;
    }
    public String setDob() {
        return dob;
    }
    public String setGender() {
        return gender;
    }
}
