package com.example.newanimals.db;

import java.util.Date;

public class ForumData {
    private String name;
    private String text;
    private String img;
    private Long time;

    public ForumData(String name, String text, String img) {
        this.name = name;
        this.text = text;
        this.img = img;
        time = new Date().getTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

