package com.ygaps.travelapp.API.Requests;

public class VerifyCode_EmailVerificationRequest {
    private  int userId;
    private String type;
    private String verifyCode;

    public VerifyCode_EmailVerificationRequest(int userId, String type, String verifyCode) {
        this.userId = userId;
        this.type = type;
        this.verifyCode = verifyCode;
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

    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
