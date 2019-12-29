package com.ygaps.travelapp.API.Responses;

public class SendEmailVerificationResponse {
    private String userId;
    private String sendTo;
    private double expiredOn;
    public SendEmailVerificationResponse(String userId, String sendTo) {
        this.userId = userId;
        this.sendTo = sendTo;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendTo() {
        return sendTo;
    }
    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public double getExpiredOn() {
        return expiredOn;
    }
    public void setExpiredOn(double expiredOn) {
        this.expiredOn = expiredOn;
    }
}
