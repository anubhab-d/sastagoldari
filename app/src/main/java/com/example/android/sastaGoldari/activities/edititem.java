package com.example.android.sastaGoldari.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class edititem extends AppCompatActivity {
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    Uri imgUri;
    FirebaseStorage storage;
    TextView etProductName;
    TextView etProductPrice;
    Button btnEditItem;
    Spinner dropdown;
    ImageView imgEdit;
    FloatingActionButton fbtnImgEdit;
    String id;
    String imgL = "https://firebasestorage.googleapis.com/v0/b/sastagoldari.appspot.com/o/download.png?alt=media&token=bf58ab64-da61-4f38-b570-1e897906f8c3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edititem);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dropdown = findViewById(R.id.spinnerUnit);
        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        btnEditItem = findViewById(R.id.btnEditItem);
        fbtnImgEdit = findViewById(R.id.fbtnImgEdit);
        imgEdit = findViewById(R.id.imgEdit);
        id = getIntent().getStringExtra("id");
        String[] items = new String[]{"kg", "qty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        fbtnImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(edititem.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etProductName.getText().toString().isEmpty()) {
                    etProductName.setError("Item's Name can't be empty");
                }
                if (etProductPrice.getText().toString().isEmpty()) {
                    etProductPrice.setError("Item's Price can't be empty");
                }
                if (!etProductName.getText().toString().isEmpty() && !etProductPrice.getText().toString().isEmpty()) {
                    imgUpdate();
                }
            }
        });
    }

    private void imgUpdate() {
        if (imgUri != null) {
            progressDialog.show();
            progressDialog.setProgress(0);
            storage.getReference()
                    .child("images")
                    .child("image" + imgUri.getLastPathSegment())
                    .putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storage.getReference()
                                    .child("images")
                                    .child("image" + imgUri.getLastPathSegment())
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imgL = String.valueOf(uri);
                                            Log.d("chk_img1", imgL);
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("name", etProductName.getText().toString());
                                            data.put("price", etProductPrice.getText().toString());
                                            data.put("unit", dropdown.getSelectedItem());
                                            data.put("imgL",imgL);
                                            firestore.collection("items").document(id)
                                                    .update(data)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            ConstCode.showToast(edititem.this, "Item Successfully Edited");
                                                            Intent i = new Intent(edititem.this, SelectEditActivity.class);
                                                            startActivity(i);
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            ConstCode.showToast(edititem.this, "Sorry! Can't Edit Your Item.");
                                                        }
                                                    });
                                        }
                                    });
                            Log.d("chk_img2", imgL);

                            progressDialog.dismiss();
                            ConstCode.showToast(edititem.this, "upload successful.");
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double cnt = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    progressDialog.setProgress((int) cnt);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ConstCode.showToast(edititem.this, "Can't Upload. Please Try again....");
                    Log.d("chk_exe", e.toString());
                    progressDialog.dismiss();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imgUri = data.getData();
        imgEdit.setImageURI(imgUri);

    }
}