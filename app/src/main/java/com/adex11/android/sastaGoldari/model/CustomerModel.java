package com.adex11.android.sastaGoldari.model;

public class CustomerModel {
    String name;
    String address;
    String para;
    String phone;
    String id;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPara() {
        return para;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }
    public CustomerModel(String name, String address, String para, String phone,String id) {
        this.name = name;
        this.address = address;
        this.para = para;
        this.phone = phone;
        this.id = id;
    }
}
