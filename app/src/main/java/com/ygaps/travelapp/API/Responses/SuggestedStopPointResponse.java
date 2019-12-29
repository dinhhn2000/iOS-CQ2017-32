package com.ygaps.travelapp.API.Responses;

import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;

public class SuggestedStopPointResponse {
    private ArrayList<StopPoint> stopPoints;

    public SuggestedStopPointResponse(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }

    @Override
    public String toString() {
        return "SuggestedStopPointResponse{" +
                "stopPoints=" + stopPoints +
                '}';
    }

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }
}
