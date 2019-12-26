package com.ygaps.travelapp.API;

public class RegisterRequest {
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Integer id;

    public RegisterRequest(String password, String fullName, String email, String phone )
    {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;

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

    public String getPassword() {
        return password;
    }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() {
        return phone;
    }

    public Integer getId() {
        return id;
    }
}
