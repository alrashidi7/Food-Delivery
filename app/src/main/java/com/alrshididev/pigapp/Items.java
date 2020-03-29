package com.alrshididev.pigapp;

public class Items {
    private String item_name;
    private String item_desc;
    private int item_price;
    private int item_quentity;
    private String item_image;

    public Items(String item_name, String item_desc, int item_price, int item_quentity, String item_image) {
        this.item_name = item_name;
        this.item_desc = item_desc;
        this.item_price = item_price;
        this.item_quentity = item_quentity;
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public int getItem_quentity() {
        return item_quentity;
    }

    public void setItem_quentity(int item_quentity) {
        this.item_quentity = item_quentity;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }
}
