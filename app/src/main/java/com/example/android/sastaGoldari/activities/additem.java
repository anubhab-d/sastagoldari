package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.databinding.ActivityAdditemBinding;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class additem extends AppCompatActivity {
    ImageView cover;
    FloatingActionButton fab;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdditemBinding b = ActivityAdditemBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        firestore = FirebaseFirestore.getInstance();
        String[] items = new String[]{"kg", "qty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        cover = (ImageView)findViewById(R.id.imageView3);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(additem.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });




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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        cover.setImageURI(uri);

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