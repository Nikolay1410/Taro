package com.example.taro;

public class Card {
    private int id;
    private String name;
    private String img;
    private String url;

    public Card(int id, String name, String img, String url) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.url = url;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
