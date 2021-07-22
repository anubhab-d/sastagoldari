package com.adex11.android.sastaGoldari.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adex11.android.sastaGoldari.R;
import com.adex11.android.sastaGoldari.adapter.ProductMaintainAdapter;
import com.adex11.android.sastaGoldari.interfaces.OnProductButtonsClicked;
import com.adex11.android.sastaGoldari.model.SellingItems;
import com.adex11.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SelectEditActivity extends AppCompatActivity implements OnProductButtonsClicked {
    ArrayList<SellingItems> list = new ArrayList<>();
    FirebaseFirestore firestore;
    ProductMaintainAdapter adapter = new ProductMaintainAdapter(this,this);
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_edit);
        RecyclerView rvProduct = findViewById(R.id.rvProduct);
        FloatingActionButton btnAddItem = findViewById(R.id.btnAddItem);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        ImageView imgViewOrder = findViewById(R.id.imgViewOrder);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String price = document.getString("price");
                                String unit = document.getString("unit");
                                String imgL = document.getString("imgL");
                                String id = document.getId();
                                list.add(new SellingItems(name, price, unit, id, imgL));
                                progressDialog.dismiss();
                                adapter.updateList(list);
                                rvProduct.setAdapter(adapter);
                            }
                        } else {
                            progressDialog.dismiss();
                            ConstCode.showToast(SelectEditActivity.this, "Sorry!! Can't Load Your Items.");
                        }
                    }
                });
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectEditActivity.this, additem.class);
                startActivity(i);
            }
        });

        imgViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectEditActivity.this, vieworder.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onEditButtonClicked(String id) {
        Intent i = new Intent(SelectEditActivity.this, edititem.class);
        i.putExtra("id", id);
        startActivity(i);
    }

    @Override
    public void onDeleteButtonClicked(int pos,String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(SelectEditActivity.this);
        dialog.setTitle("⚠ Alert ⚠");
        dialog.setCancelable(false);
        dialog.setMessage("Are you want to delete this item?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(pos);
                adapter.updateList(list);
                firestore.collection("items").document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                ConstCode.showToast(SelectEditActivity.this,"Item has deleted successfully");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ConstCode.showToast(SelectEditActivity.this,"Sorry Can't delete Item");
                    }
                });
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
      dialog.show();
    }
}