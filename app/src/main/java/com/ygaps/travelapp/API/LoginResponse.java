package com.ygaps.travelapp.API;

public class LoginResponse {
    private String userId;
    private boolean emailVerified;
    private boolean phoneVerified;
    private String token;

    public LoginResponse(String userId, boolean emailVerified, boolean phoneVerified, String token) {
        this.userId = userId;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.token = token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public String getToken() {
        return token;
    }
}
