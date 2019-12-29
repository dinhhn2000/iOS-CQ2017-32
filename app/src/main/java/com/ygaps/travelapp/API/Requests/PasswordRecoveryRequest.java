package com.ygaps.travelapp.API.Requests;

public class PasswordRecoveryRequest {
    private int userId;
    private String newPassword;
    private String verifyCode;

    public PasswordRecoveryRequest(int userId, String newPassword, String verifyCode) {
        this.userId = userId;
        this.newPassword = newPassword;
        this.verifyCode = verifyCode;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
