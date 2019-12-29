package com.ygaps.travelapp.API.Requests;

public class TourInfoRequest {
    private int tourId;

    public TourInfoRequest(int tourId) {
        this.tourId = tourId;
    }

    public int getTourId() {
        return tourId;
    }
    public void setTourId(int tourId) {
        this.tourId = tourId;
    }
}
