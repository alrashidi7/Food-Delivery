package com.alrshididev.pigapp;

public class RestourantsMenu {
    private String name;
    private String status;
    private String rate;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public RestourantsMenu(String name, String status, String rate, String image) {
        this.name = name;
        this.status = status;
        this.rate = rate;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
