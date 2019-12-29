package com.ygaps.travelapp.API.Requests;

public class SendCommentRequest {
    private long tourId;
    private String comment;

    public SendCommentRequest(long tourId, String comment) {
        this.tourId = tourId;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SendCommentRequest{" +
                "tourId=" + tourId +
                ", comment='" + comment + '\'' +
                '}';
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
