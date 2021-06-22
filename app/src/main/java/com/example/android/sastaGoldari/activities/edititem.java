package com.example.android.sastaGoldari.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class edititem extends AppCompatActivity {
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);
        firestore = FirebaseFirestore.getInstance();
        Spinner dropdown = findViewById(R.id.spinnerUnit);
        TextView etProductName = findViewById(R.id.etProductName);
        TextView etProductPrice = findViewById(R.id.etProductPrice);
        Button btnEditItem = findViewById(R.id.btnEditItem);
        String id = getIntent().getStringExtra("id");
        String[] items = new String[]{"kg", "qty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> data = new HashMap<>();
                data.put("name",etProductName.getText().toString());
                data.put("price",etProductPrice.getText().toString());
                data.put("unit",dropdown.getSelectedItem());
                firestore.collection("items").document(id)
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ConstCode.showToast(edititem.this,"Item Successfully Edited");
                                Intent i = new Intent(edititem.this,SelectEditActivity.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ConstCode.showToast(edititem.this,"Sorry! Can't Edit Your Item.");
                            }
                        });
            }
        });
    }
}