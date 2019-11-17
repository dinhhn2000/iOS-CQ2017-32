package com.example.travelsupporter.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateTourResponse {
    @SerializedName("hostId")
    @Expose
    private String hostId;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("minCost")
    @Expose
    private int minCost;

    @SerializedName("maxCost")
    @Expose
    private int maxCost;

    @SerializedName("startDate")
    @Expose
    private int startDate;

    @SerializedName("endDate")
    @Expose
    private int endDate;

    @SerializedName("adults")
    @Expose
    private int adults;

    @SerializedName("childs")
    @Expose
    private int childs;

    @SerializedName("sourceLat")
    @Expose
    private float sourceLat;

    @SerializedName("sourceLong")
    @Expose
    private float sourceLong;

    @SerializedName("desLat")
    @Expose
    private float desLat;

    @SerializedName("desLong")
    @Expose
    private float desLong;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("isPrivate")
    @Expose
    private boolean isPrivate;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public CreateTourResponse(String hostId, int status, String name, int minCost, int maxCost, int startDate, int endDate, int adults,
                              int childs, float sourceLat, float sourceLong, float desLat, float desLong, int id, boolean isPrivate, String avatar) {
        this.hostId = hostId;
        this.status = status;
        this.name = name;
        this.minCost = minCost;

        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;

        this.childs = childs;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;

        this.desLong = desLong;
        this.id = id;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setStartTime(int startDate) {
        this.startDate = startDate;
    }

    public void setEndTime(int endDate) {
        this.endDate = endDate;
    }

    public void setSourceLat(float sourceLat) {
        this.sourceLat = sourceLat;
    }

    public void setSourceLong(float sourceLong) {
        this.sourceLong = sourceLong;
    }

    public void setDesLat(float desLat) {
        this.desLat = desLat;
    }

    public void setDesLong(float desLong) {
        this.desLong = desLong;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setchilds(int childs) {
        this.childs = childs;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getname() {
        return name;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public float getSourceLat() {
        return sourceLat;
    }

    public float getSourceLong() {
        return sourceLong;
    }

    public float getDesLat() {
        return desLat;
    }

    public float getDesLong() {
        return desLong;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public int getAdults() {
        return adults;
    }

    public int getchilds() {
        return childs;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getHostId() {
        return hostId;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}