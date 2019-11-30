package com.example.travelsupporter.utils;

public class Tour {
    private long id;
    private long status;
    private String name;
    private String minCost;
    private String maxCost;
    private String startDate;
    private String endDate;
    private int adult;
    private int child;
    private boolean isPrivate;
    private String avatar;

    public Tour(long id, long status, String name, String minCost, String maxCost, String startDate, String endDate, int adult, int child, boolean isPrivate, String avatar) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adult = adult;
        this.child = child;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public long getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getMinCost() {
        return minCost;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getAdult() {
        return adult;
    }

    public int getChild() {
        return child;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }
}