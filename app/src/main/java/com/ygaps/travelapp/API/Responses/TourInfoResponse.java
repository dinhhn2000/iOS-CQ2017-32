package com.ygaps.travelapp.API.Responses;

public class TourInfoResponse {
    private int id;
    private String hostId;
    private int status;
    private String name;
    private String minCost;
    private String maxCost;
    private String startDate;
    private String endDate;
    private int adults;
    private int childs;
    private boolean isPrivate;


    public TourInfoResponse(int id, String hostId, int status, String name, String minCost, String maxCost, String startDate, String endDate, int adults, int childs, boolean isPrivate) {
        this.id = id;
        this.hostId = hostId;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adults = adults;
        this.childs = childs;
        this.isPrivate = isPrivate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getHostId() {
        return hostId;
    }
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMinCost() {
        return minCost;
    }
    public void setMinCost(String minCost) {
        this.minCost = minCost;
    }

    public String getMaxCost() {
        return maxCost;
    }
    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAdults() {
        return adults;
    }
    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChilds() {
        return childs;
    }
    public void setChilds(int childs) {
        this.childs = childs;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }
    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}
