package com.ygaps.travelapp.API;

public class LoginFacebookRequest {
    private String accessToken;

    public LoginFacebookRequest(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
