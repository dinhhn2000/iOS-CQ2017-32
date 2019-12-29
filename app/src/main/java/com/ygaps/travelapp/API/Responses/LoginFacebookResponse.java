package com.ygaps.travelapp.API.Responses;

public class LoginFacebookResponse {
    private long userId;
    private String token;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LoginFacebookResponse(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
