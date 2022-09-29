package com.example.tarogame;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cardstaro")
public class Card {
    @PrimaryKey
    private int id;
    private String name;
    private String img;
    private String url;
    private String contentPr;
    private String contentPer;

    public Card(int id, String name, String img, String url, String contentPr, String contentPer) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.url = url;
        this.contentPr = contentPr;
        this.contentPer = contentPer;
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

    public String getImg() {return img;}

    public void setImg(String img) {this.img = img;}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentPr() {
        return contentPr;
    }

    public void setContentPr(String contentPr) {
        this.contentPr = contentPr;
    }

    public String getContentPer() {
        return contentPer;
    }

    public void setContentPer(String contentPer) {
        this.contentPer = contentPer;
    }
}
