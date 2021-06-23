package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.android.sastaGoldari.databinding.ActivityAdditemBinding;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class additem extends AppCompatActivity {
private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdditemBinding b = ActivityAdditemBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        firestore = FirebaseFirestore.getInstance();
        String[] items = new String[]{"kg", "qty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        b.spinner.setAdapter(adapter);
        b.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b.etItemName.getText().toString().isEmpty()){
                    b.etItemName.setError("Item's Name can't be empty");
                }
                if(b.etItemPrice.getText().toString().isEmpty()){
                    b.etItemPrice.setError("Item's Price can't be empty");
                }
                if(!b.etItemName.getText().toString().isEmpty() && !b.etItemPrice.getText().toString().isEmpty()) {
                    addItem(b.etItemName.getText().toString()
                            , b.etItemPrice.getText().toString()
                            , b.spinner.getSelectedItem().toString()
                    );
                }
            }
        });
    }

    private void addItem(String name, String price, String unit) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", price);
        item.put("unit", unit);
        firestore.collection("items")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        ConstCode.showToast(additem.this,"Item Added Successfully");
                        Intent i = new Intent(additem.this,SelectEditActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Sorry not able to upload items");
                    }
                });
    }

    private void showToast(String txt){
        Toast.makeText(additem.this,txt,Toast.LENGTH_SHORT).show();
    }
}