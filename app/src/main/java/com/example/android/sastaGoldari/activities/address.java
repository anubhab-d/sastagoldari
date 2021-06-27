package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
ArrayList<String> itemUnit = new ArrayList<>();
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
            itemUnit.add(model.getUnit());
            itemPrice.add(model.getPrice());
        }
       b.orderNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(b.etCusName.getText().toString().isEmpty()){
                    b.etCusName.setError("Customer name can't be empty");
                }
                if(b.etCusAddress.getText().toString().isEmpty()){
                    b.etCusAddress.setError("Address can't be empty");
                }
                if(b.etCusPara.getText().toString().isEmpty()){
                    b.etCusPara.setError("Para can't be empty");
                }
                if(b.etCusPh.getText().toString().isEmpty()){
                    b.etCusPh.setError("Phone number can't be empty");
                }
                if(!b.etCusName.getText().toString().isEmpty() && !b.etCusAddress.getText().toString().isEmpty() && !b.etCusPara.getText().toString().isEmpty() && !b.etCusPh.getText().toString().isEmpty()) {
                    addItem(
                            b.etCusName.getText().toString(),
                            b.etCusAddress.getText().toString(),
                            b.etCusPara.getText().toString(),
                            b.etCusPh.getText().toString(),
                            itemNames,
                            itemQty,
                            itemPrice,
                            itemUnit
                    );
                }




            }
        });

    }
    private void addItem(String name,
                         String address,
                         String para,
                         String phone,
                         List<String> orderItemNames,
                         List<String> orderItemQty,
                         List<String> orderItemPrice,
                         List<String> orderItemUnit) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("address", address);
        item.put("para", para);
        item.put("phone", phone);
        item.put("orderItemNames", orderItemNames);
        item.put("orderItemQty", orderItemQty);
        item.put("orderItemPrice", orderItemPrice);
        item.put("orderItemUnit", orderItemUnit);
        item.put("orderStatus","pending");
        firestore.collection("orders")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ConstCode.showToast(address.this,"Order Placed Successfully.");
                        AlertDialog.Builder dialog = new AlertDialog.Builder(address.this);
                        dialog.setTitle("🙏 Thank You 🙏");
                        dialog.setMessage("Your Order is Placed Successfully!!!");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("Continue Shopping", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(address.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", false);
                                startActivity(intent);
                            }
                        });
                        dialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    // finish();
                                Intent intent = new Intent(address.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        });
                        dialog.show();
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
