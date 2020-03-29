package com.alrshididev.pigapp;

public class OrderItems {
    private int id;
    private String name;
    private int phone;
    private String item_name;
    private int item_price;
    private int item_quentity;
    private String item_imgae;
    private int total;

    public OrderItems(int id,String name, int phone, String item_name, int item_price, int item_quentity, String item_imgae,int total) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_quentity = item_quentity;
        this.item_imgae = item_imgae;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
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

    public String getItem_imgae() {
        return item_imgae;
    }

    public void setItem_imgae(String item_imgae) {
        this.item_imgae = item_imgae;
    }
}
