package com.ygaps.travelapp.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StopPoint implements Serializable {
    private int id;
    private String name;
    private String address;
    private int provinceId;
    private double lat;
    @SerializedName("long")
    @Expose
    private double Long;
    private long arrivalAt;
    private long leaveAt;
    private int serviceTypeId;
    private int minCost;
    private int maxCost;
    private int serviceId;

    public StopPoint(String name, String address, int provinceId, double lat, double aLong, long arrivalAt, long leaveAt, int serviceTypeId, int minCost, int maxCost) {
        Long = aLong;
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.lat = lat;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    public StopPoint(int id, String name, String address, int provinceId, double lat, double aLong, long arrivalAt, long leaveAt, int serviceTypeId, int minCost, int maxCost) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.lat = lat;
        Long = aLong;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    public StopPoint(int id, String name, String address, int provinceId, double lat, double aLong, long arrivalAt, long leaveAt, int serviceTypeId, int minCost, int maxCost, int serviceId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.provinceId = provinceId;
        this.lat = lat;
        Long = aLong;
        this.arrivalAt = arrivalAt;
        this.leaveAt = leaveAt;
        this.serviceTypeId = serviceTypeId;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.serviceId = serviceId;
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
                ", arrivalAt=" + arrivalAt +
                ", leaveAt=" + leaveAt +
                ", serviceTypeId=" + serviceTypeId +
                ", minCost=" + minCost +
                ", maxCost=" + maxCost +
                ", serviceId=" + serviceId +
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

    public long getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(long arrivalAt) {
        this.arrivalAt = arrivalAt;
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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
