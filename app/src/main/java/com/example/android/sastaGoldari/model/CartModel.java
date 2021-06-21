package com.example.android.sastaGoldari.model;

public class CartModel {
    String id;
    String qty;
    public String getId() {
        return id;
    }

    public String getQty() {
        return qty;
    }
    public CartModel(String id,String qty) {
        this.id = id;
        this.qty = qty;
    }
}
