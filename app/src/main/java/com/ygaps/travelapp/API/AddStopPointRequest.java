package com.ygaps.travelapp.API;

import com.ygaps.travelapp.utils.StopPoint;

public class AddStopPointRequest {
    private String tourId;
    private StopPoint[] stopPoints;
    private int[] deleteIds;

    public AddStopPointRequest(String tourId, StopPoint[] stopPoints, int[] deleteIds) {
        this.tourId = tourId;
        this.stopPoints = stopPoints;
        this.deleteIds = deleteIds;
    }

    public AddStopPointRequest(String tourId, StopPoint[] stopPoints) {
        this.tourId = tourId;
        this.stopPoints = stopPoints;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public StopPoint[] getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(StopPoint[] stopPoints) {
        this.stopPoints = stopPoints;
    }

    public int[] getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(int[] deleteIds) {
        this.deleteIds = deleteIds;
    }
}
