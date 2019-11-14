package com.example.travelsupporter.API;

public class CreateTourRequest {
    private String tourName;
    private String startDate;
    private String endDate;

    private boolean isPrivate;
    private String adults;
    private String children;
    private String minCost;
    private String maxCost;
    private String avatar;
    public CreateTourRequest (String tourName, String startTime, String endTime, boolean isPrivate, String adults, String children, String minCost, String maxCost, String avatar ){
        this.tourName = tourName;
        this.startDate = startTime;
        this.endDate = endTime;

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
    public void setStartTime(String startDate) {
        this.startDate = startDate;
    }
    public void setEndTime(String endDate) {
        this.endDate = endDate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public void setAdults(String adults) {
        this.adults = adults;
    }
    public void setChildren(String children) {
        this.children = children;
    }
    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }
    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTourName() {
        return tourName;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
    public String getAdults() {
        return adults;
    }
    public String getChildren() {
        return children;
    }
    public String getMinCost() {
        return minCost;
    }
    public String getMaxCost() {
        return maxCost;
    }
    public String getAvatar() {
        return avatar;
    }

}
