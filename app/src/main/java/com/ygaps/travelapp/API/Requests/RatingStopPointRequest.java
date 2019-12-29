package com.ygaps.travelapp.API.Requests;

public class RatingStopPointRequest {
    private long serviceId;
    private String feedback;
    private int point;

    public RatingStopPointRequest(long serviceId, String feedback, int point) {
        this.serviceId = serviceId;
        this.feedback = feedback;
        this.point = point;
    }

    @Override
    public String toString() {
        return "RatingStopPointRequest{" +
                "serviceId=" + serviceId +
                ", feedback='" + feedback + '\'' +
                ", point=" + point +
                '}';
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
