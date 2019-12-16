package com.example.travelsupporter.API;

public class MessageResponse {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public MessageResponse(String message) {
        this.message = message;
    }
}
