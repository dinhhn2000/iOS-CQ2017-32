package com.ygaps.travelapp.API.Responses;

import com.ygaps.travelapp.utils.Comment;
import com.ygaps.travelapp.utils.Member;
import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;

public class getTourInfoResponse {
    private long id;
    private long hostId;
    private int status;
    private String name;
    private long minCost;
    private long maxCost;
    private long startDate;
    private long endDate;
    private int adult;
    private int childs;
    private boolean isPrivate;
    private String avatar;
    private ArrayList<StopPoint> stopPoints;
    private ArrayList<Comment> comments;
    private ArrayList<Member> members;

    public getTourInfoResponse(long id, long hostId, int status, String name, long minCost,
                               long maxCost, long startDate, long endDate, int adult, int childs,
                               boolean isPrivate, String avatar, ArrayList<StopPoint> stopPoints,
                               ArrayList<Comment> comments, ArrayList<Member> members) {
        this.id = id;
        this.hostId = hostId;
        this.status = status;
        this.name = name;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
        this.adult = adult;
        this.childs = childs;
        this.isPrivate = isPrivate;
        this.avatar = avatar;
        this.stopPoints = stopPoints;
        this.comments = comments;
        this.members = members;
    }

    @Override
    public String toString() {
        return "getTourInfoResponse{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", minCost=" + minCost +
                ", maxCost=" + maxCost +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", adult=" + adult +
                ", childs=" + childs +
                ", isPrivate=" + isPrivate +
                ", avatar='" + avatar + '\'' +
                ", stopPoints=" + stopPoints +
                ", comments=" + comments +
                ", members=" + members +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHostId() {
        return hostId;
    }

    public void setHostId(long hostId) {
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

    public long getMinCost() {
        return minCost;
    }

    public void setMinCost(long minCost) {
        this.minCost = minCost;
    }

    public long getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(long maxCost) {
        this.maxCost = maxCost;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public int getAdult() {
        return adult;
    }

    public void setAdult(int adult) {
        this.adult = adult;
    }

    public int getChilds() {
        return childs;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<StopPoint> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(ArrayList<StopPoint> stopPoints) {
        this.stopPoints = stopPoints;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}
