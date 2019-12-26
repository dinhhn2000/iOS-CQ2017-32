package com.ygaps.travelapp.API;

public class RequestOTP_PasswordRecoveryResponse {
    private int userId;
    private String type;
    private String expiredOn;

    public RequestOTP_PasswordRecoveryResponse(int userId, String type, String expiredOn) {
        this.userId = userId;
        this.type = type;
        this.expiredOn = expiredOn;
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

    public String getExpiredOn() {
        return expiredOn;
    }
    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn;
    }



}
