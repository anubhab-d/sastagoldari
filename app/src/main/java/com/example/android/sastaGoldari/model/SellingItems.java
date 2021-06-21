package com.example.android.sastaGoldari.model;

public class SellingItems {
    String name;
    String price;
    String unit;
    String id;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }

    public String getId() {
        return id;
    }

    public SellingItems(String name, String price, String unit, String id) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.id = id;
    }
}
