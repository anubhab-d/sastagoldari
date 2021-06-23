package com.example.android.sastaGoldari.model;

import java.util.List;

public class CustomerModel {
    String name;
    String address;
    String para;
    String phone;
 //   List<CartModel> cartList;

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

//    public List<CartModel> getCartList() {
//        return cartList;
//    }
    public CustomerModel(String name, String address, String para, String phone) {
        this.name = name;
        this.address = address;
        this.para = para;
        this.phone = phone;
      //  this.cartList = cartList;
    }
}
