package com.example.travelsupporter.API;

import com.example.travelsupporter.utils.Tour;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TourListResponse {
    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("tours")
    @Expose
    private ArrayList<Tour> tours;

    public Integer getTotal() {
        return total;
    }

    public ArrayList<Tour> getTours() {
        return tours;
    }
}