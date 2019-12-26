package com.ygaps.travelapp.API;

public class SendEmailVerificationRequest {
    private int userId;
    private String type;

    public SendEmailVerificationRequest(int userId, String type) {
        this.userId = userId;
        this.type = type;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
