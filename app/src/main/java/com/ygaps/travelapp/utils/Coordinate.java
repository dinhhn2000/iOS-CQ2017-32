package com.ygaps.travelapp.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate{
    private double lat;
    @SerializedName("long")
    @Expose
    private double Long;

    public Coordinate(double lat, double aLong) {
        this.lat = lat;
        Long = aLong;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLong() {
        return Long;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }
}
