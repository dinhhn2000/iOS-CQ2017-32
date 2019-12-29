package com.ygaps.travelapp.API.Requests;

import com.ygaps.travelapp.utils.Coordinate;


public class SuggestedStopPointRequest {
    private boolean hasOneCoordinate;
    private Coordinate coordList;

    public SuggestedStopPointRequest(boolean hasOneCoordinate, Coordinate coordList) {
        this.hasOneCoordinate = hasOneCoordinate;
        this.coordList = coordList;
    }

    public boolean isHasOneCoordinate() {
        return hasOneCoordinate;
    }

    public void setHasOneCoordinate(boolean hasOneCoordinate) {
        this.hasOneCoordinate = hasOneCoordinate;
    }

    public Coordinate getCoordList() {
        return coordList;
    }

    public void setCoordList(Coordinate coordList) {
        this.coordList = coordList;
    }
}

