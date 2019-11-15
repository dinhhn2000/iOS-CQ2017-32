package com.example.travelsupporter.API;

public class RegisterRequest {
    private String password;
    private String fullName;
    private String email;
    private String phone;

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

    public String setPassword() {
        return password;
    }
    public String setFullName() { return fullName; }
    public String setEmail() { return email; }
    public String setPhone() {
        return phone;
    }

}
