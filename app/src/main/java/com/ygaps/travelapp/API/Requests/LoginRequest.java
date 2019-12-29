package com.ygaps.travelapp.API.Requests;

public class LoginRequest {
    private String emailPhone;
    private String password;

    public LoginRequest(String emailPhone, String password) {
        this.emailPhone = emailPhone;
        this.password = password;
    }

    public void setEmailPhone(String emailPhone) {
        this.emailPhone = emailPhone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailPhone() {
        return emailPhone;
    }

    public String getPassword() {
        return password;
    }
}
