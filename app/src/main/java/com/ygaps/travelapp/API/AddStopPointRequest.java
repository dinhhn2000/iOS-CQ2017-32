package com.ygaps.travelapp.API;

import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class AddStopPointRequest {
    private String tourId;
    private ArrayList<StopPoint> stopPoints;
    private int[] deleteIds;

    public AddStopPointRequest(String tourId, ArrayList<StopPoint> stopPoints, int[] deleteIds) {
        this.tourId = tourId;
        this.stopPoints = stopPoints;
        this.deleteIds = deleteIds;
    }

    @Override
    public String toString() {
        return "AddStopPointRequest{" +
                "tourId='" + tourId + '\'' +
                ", stopPoints=" + stopPoints +
                ", deleteIds=" + Arrays.toString(deleteIds) +
                '}';
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }

    public int[] getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(int[] deleteIds) {
        this.deleteIds = deleteIds;
    }
}
