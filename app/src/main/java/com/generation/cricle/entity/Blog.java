package com.generation.cricle.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Blog implements Serializable {
    private Long id;
    private String title;
    private List<String> pictureList;
    private String content;
    private String createdAt;
    private String name;
    private String avatar;


    public Blog(String title, List<String> pictureList, String content, String createdAt) {
        this.title = title;
        this.pictureList = pictureList;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public String getContent() {
        return content;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
