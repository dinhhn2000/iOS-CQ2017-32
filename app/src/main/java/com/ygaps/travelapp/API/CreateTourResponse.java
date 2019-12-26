package com.ygaps.travelapp.API;

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
    private long startDate;

    @SerializedName("endDate")
    @Expose
    private long endDate;

    @SerializedName("adults")
    @Expose
    private int adults;

    @SerializedName("childs")
    @Expose
    private int childs;

    @SerializedName("sourceLat")
    @Expose
    private double sourceLat;

    @SerializedName("sourceLong")
    @Expose
    private double sourceLong;

    @SerializedName("desLat")
    @Expose
    private double desLat;

    @SerializedName("desLong")
    @Expose
    private double desLong;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("isPrivate")
    @Expose
    private boolean isPrivate;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public void setSourceLat(double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public void setSourceLong(double sourceLong) {
        this.sourceLong = sourceLong;
    }

    public void setDesLat(double desLat) {
        this.desLat = desLat;
    }

    public void setDesLong(double desLong) {
        this.desLong = desLong;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHostId() {
        return hostId;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getMinCost() {
        return minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChilds() {
        return childs;
    }

    public double getSourceLat() {
        return sourceLat;
    }

    public double getSourceLong() {
        return sourceLong;
    }

    public double getDesLat() {
        return desLat;
    }

    public double getDesLong() {
        return desLong;
    }

    public int getId() {
        return id;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public CreateTourResponse(String hostId, int status, String name, int minCost, int maxCost, long startDate, long endDate, int adults, int childs, double sourceLat, double sourceLong, double desLat, double desLong, int id, boolean isPrivate, String avatar) {
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
}