package com.example.android.sastaGoldari.model;

public class CartModel {
    String name;
    String price;
    String unit;
    String qty;
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public String getUnit() {
        return unit;
    }
    public String getQty() { return qty; }
    public CartModel(String name, String price, String unit, String qty) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.qty = qty;
    }
}
