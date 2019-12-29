package com.ygaps.travelapp.API.Responses;

public class VerifyCode_EmailVerificationResponse {
    private boolean success;

    public VerifyCode_EmailVerificationResponse(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
