package com.example.android.sastaGoldari.model;

public class SellingItems {
    String name;
    String price;
    String unit;
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public SellingItems(String name, String price, String unit) {
        this.name = name;
        this.price = price;
        this.unit=unit;
    }


}
