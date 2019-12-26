package com.ygaps.travelapp.API;

public class CreateTourRequest {
    private String name;
    private long startDate;
    private long endDate;
    private double sourceLat;
    private double sourceLong;
    private double desLat;
    private double desLong;
    private boolean isPrivate;
    private int adults;
    private int children;
    private int maxCost;
    private int minCost;
    private String avatar;

    public CreateTourRequest(String name, long startDate, long endDate, double sourceLat, double sourceLong, double desLat, double desLong, boolean isPrivate, int adults, int children, int maxCost, int minCost) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.children = children;
        this.maxCost = maxCost;
        this.minCost = minCost;
    }

    public CreateTourRequest(String name, long startDate, long endDate, double sourceLat, double sourceLong, double desLat, double desLong, boolean isPrivate, int adults, int children, int maxCost, int minCost, String avatar) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.desLat = desLat;
        this.desLong = desLong;
        this.isPrivate = isPrivate;
        this.adults = adults;
        this.children = children;
        this.maxCost = maxCost;
        this.minCost = minCost;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
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

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
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

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }
}
