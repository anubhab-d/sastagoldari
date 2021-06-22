package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.sastaGoldari.databinding.ActivityAddressBinding;
import com.example.android.sastaGoldari.model.CartModel;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class address extends AppCompatActivity {
private FirebaseFirestore firestore;
ArrayList<String> itemNames = new ArrayList<>();
ArrayList<String> itemQty = new ArrayList<>();
ArrayList<String> itemPrice = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        ActivityAddressBinding b = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        for(int i = 0; i< MainActivity.cartList.size(); i++){
            CartModel model = MainActivity.cartList.get(i);
            itemNames.add(model.getName());
            itemQty.add(model.getQty());
            double total = Double.parseDouble(MainActivity.cartList.get(i).getPrice().substring(1)) * Double.parseDouble(MainActivity.cartList.get(i).getQty());
            itemPrice.add(Double.toString(total));
        }
       b.orderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(
                     b.etCusName.getText().toString(),
                        b.etCusAddress.getText().toString(),
                        b.etCusPara.getText().toString(),
                        b.etCusPh.getText().toString(),
                        itemNames,
                        itemQty,
                        itemPrice
                );
            }
        });

    }
    private void addItem(String name,
                         String address,
                         String para,
                         String phone,
                         List<String> orderItemNames,
                         List<String> orderItemQty,
                         List<String> orderItemPrice) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("address", address);
        item.put("para", para);
        item.put("phone", phone);
        item.put("orderItemNames", orderItemNames);
        item.put("orderItemQty", orderItemQty);
        item.put("orderItemPrice", orderItemPrice);
        item.put("orderStatus","pending");
        firestore.collection("orders")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ConstCode.showToast(address.this,"Order Placed Successfully.");
                        Intent i = new Intent(address.this, thankyou.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ConstCode.showToast(address.this,"Sorry not able to place order.");
                    }
                });
    }
}
