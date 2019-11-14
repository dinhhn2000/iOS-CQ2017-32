package com.example.travelsupporter.API;

public class CreateTourResponse {
    private String tourName;
    private int startDate;
    private int endDate;
    private float sourceLat;
    private float sourceLong;
    private float desLat;
    private float desLong;
    private boolean isPrivate;
    private int adults;
    private int children;
    private int minCost;
    private int maxCost;
    private String avatar;
    public CreateTourResponse (String tourName, int startTime, int endTime,float sourceLat,float sourceLong,float desLat,
                               float desLong, boolean isPrivate, int adults, int children, int minCost, int maxCost, String avatar ){
        this.tourName = tourName;
        this.startDate = startTime;
        this.endDate = endTime;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.children = children;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.avatar = avatar;
    }
    public void setTourName(String tourName) {
        this.tourName = tourName;
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
    public void setChildren(int children) {
        this.children = children;
    }
    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }
    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTourName() {
        return tourName;
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
    public int getChildren() {
        return children;
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
}
