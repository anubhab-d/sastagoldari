package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.adapter.ViewOrderAdapter;
import com.example.android.sastaGoldari.interfaces.OnViewOrderListBtnClicked;
import com.example.android.sastaGoldari.model.CustomerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class vieworder extends AppCompatActivity implements OnViewOrderListBtnClicked {
FirebaseFirestore firestore;
    ArrayList<CustomerModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworder);
        firestore = FirebaseFirestore.getInstance();
        RecyclerView rvViewOrder = findViewById(R.id.rvViewOrder);
        ViewOrderAdapter adapter = new ViewOrderAdapter(this,this);
        rvViewOrder.setLayoutManager(new LinearLayoutManager(this));
        firestore.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("name");
                                String address = document.getString("address");
                                String para = document.getString("para");
                                String phone = document.getString("phone");
                                list.add(new CustomerModel(name,address,para,phone));
                                adapter.updateList(list);
                                rvViewOrder.setAdapter(adapter);
                            }
                        } else {
                           // Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onListButtonClicked(int pos) {

    }
}