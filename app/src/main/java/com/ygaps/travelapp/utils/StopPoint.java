package com.ygaps.travelapp.utils;

import java.io.Serializable;

public class StopPoint implements Serializable {
    private int id;
    private String name;
    private String address;
    private int provinceId;
    private long lat;
    private long Long;
    private long arriveAt;
    private long leaveAt;
    private int serviceTypeId;
    private int minCost;
    private int maxCost;


    public StopPoint(String name, String address, int provinceId, long lat, long aLong, long arriveAt, long leaveAt, int serviceTypeId, int minCost, int maxCost) {
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.lat = lat;
        Long = aLong;
        this.arriveAt = arriveAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    @Override
    public String toString() {
        return "StopPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", provinceId=" + provinceId +
                ", lat=" + lat +
                ", Long=" + Long +
                ", arriveAt=" + arriveAt +
                ", leaveAt=" + leaveAt +
                ", serviceTypeId=" + serviceTypeId +
                ", minCost=" + minCost +
                ", maxCost=" + maxCost +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getArrivalAt() {
        return arriveAt;
    }

    public void setArrivalAt(long arriveAt) {
        this.arriveAt = arriveAt;
    }

    public long getLeaveAt() {
        return leaveAt;
    }

    public void setLeaveAt(long leaveAt) {
        this.leaveAt = leaveAt;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }
}
