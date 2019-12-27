package com.ygaps.travelapp.utils;

public class Notification {
    private int userId;
    private String name;
    private String notification;
    private String avatar;

    public Notification(int userId, String name, String notification, String avatar) {
        this.userId = userId;
        this.name = name;
        this.notification = notification;
        this.avatar = avatar;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
