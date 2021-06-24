package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class additem extends AppCompatActivity {
    ImageView cover;
    FloatingActionButton fab;
    private FirebaseFirestore firestore;
    ProgressDialog progressDialog;
    Uri imgUri;
    ActivityAdditemBinding b;
    String imgL = "https://firebasestorage.googleapis.com/v0/b/sastagoldari.appspot.com/o/download.png?alt=media&token=bf58ab64-da61-4f38-b570-1e897906f8c3";
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAdditemBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        storage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
                    imgUpload();
                }
            }
        });
    }

    private void imgUpload() {
        if(imgUri != null) {
            progressDialog.show();
            progressDialog.setProgress(0);
            storage.getReference()
                    .child("images")
                    .child("image" + imgUri.getLastPathSegment())
                    .putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ConstCode.showToast(additem.this, "upload successful.");
                            storage.getReference()
                                    .child("images")
                                    .child("image" + imgUri.getLastPathSegment())
                                    .getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imgL = String.valueOf(uri);
                                        }
                                    });
                            addItem(b.etItemName.getText().toString()
                                    , b.etItemPrice.getText().toString()
                                    , b.spinner.getSelectedItem().toString(),
                                    imgL
                            );
                            progressDialog.dismiss();
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
                    ConstCode.showToast(additem.this, "Can't Upload. Please Try again....");
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
        cover.setImageURI(imgUri);

    }





    private void addItem(String name, String price, String unit, String imgL) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", price);
        item.put("unit", unit);
        item.put("imgL",imgL);
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