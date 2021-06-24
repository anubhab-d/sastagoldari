package com.example.android.sastaGoldari.model;

public class SellingItems {
    String name;
    String price;
    String unit;
    String id;
    String imgL;

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

    public String getImgL() { return imgL; }
    public SellingItems(String name, String price, String unit, String id, String imgL) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.id = id;
        this.imgL = imgL;
    }
}
