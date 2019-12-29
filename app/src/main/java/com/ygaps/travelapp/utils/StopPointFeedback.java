package com.ygaps.travelapp.utils;

public class StopPointFeedback {
    private long id;
    private String name;
    private String phone;
    private int point;
    private String feedback;
    private long createdOn;
    private String avatar;

    public StopPointFeedback(long id, String name, String phone, int point, String feedback, long createdOn, String avatar) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.point = point;
        this.feedback = feedback;
        this.createdOn = createdOn;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "StopPointFeedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", point=" + point +
                ", feedback='" + feedback + '\'' +
                ", createdOn=" + createdOn +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
