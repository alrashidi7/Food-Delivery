package com.alrshididev.pigapp;

public class Address {
    private int id;
    private String name ;
    private int phone;
    private String address;

    public Address(int id,String name, int phone, String address) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
