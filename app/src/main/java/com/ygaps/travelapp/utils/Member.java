package com.ygaps.travelapp.utils;

public class Member {
    private long id;
    private String name;
    private String phone;
    private String avatar;
    private boolean isHost;

    public Member(long id, String name, String phone, String avatar, boolean isHost) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.avatar = avatar;
        this.isHost = isHost;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isHost=" + isHost +
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
