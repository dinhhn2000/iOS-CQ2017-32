package com.ygaps.travelapp.utils;

public class Comment {
    private long id;
    private String name;
    private String comment;
    private String avatar;

    public Comment(long id, String name, String comment, String avatar) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
