package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.adapter.ViewOrderAdapter;
import com.example.android.sastaGoldari.interfaces.OnCartListRemoveBtnClicked;
import com.example.android.sastaGoldari.model.CartModel;
import com.example.android.sastaGoldari.model.CustomerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class vieworder extends AppCompatActivity implements OnCartListRemoveBtnClicked {
FirebaseFirestore firestore;
    String name;
    String address;
    String para;
    String phone;
    String status;
    String selectedStatus;
    ArrayList<CustomerModel> list = new ArrayList<>();
//    ArrayList<CustomerModel> cancelList = new ArrayList<>();
//    ArrayList<CustomerModel> deliveredList = new ArrayList<>();
    List<CartModel> cartList = new ArrayList<>();
    AutoCompleteTextView etOrderStatus;
    ArrayAdapter statusAdapter;
    String[] statusList = {"pending","canceled","delivered"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworder);
        firestore = FirebaseFirestore.getInstance();
        etOrderStatus = findViewById(R.id.etOrderStatus);
        RecyclerView rvViewOrder = findViewById(R.id.rvViewOrder);
        ViewOrderAdapter adapter = new ViewOrderAdapter(this,this);
        rvViewOrder.setAdapter(adapter);
        rvViewOrder.setLayoutManager(new LinearLayoutManager(this));
        statusAdapter = new ArrayAdapter(this, R.layout.dropdown_item, statusList);
        etOrderStatus.setAdapter(statusAdapter);
        etOrderStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = statusList[position];
                if(selectedStatus.equals("pending")){
                    firestore.collection("orders")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            name = document.getString("name");
                                            address = document.getString("address");
                                            para = document.getString("para");
                                            phone = document.getString("phone");
                                            status = document.getString("orderStatus");
                                            String id = document.getId();
                                            if(status.equals("pending")) {
                                                list.add(new CustomerModel(name, address, para, phone, id));
                                            }
                                        }
                                        adapter.updateList(list);
                                        list.clear();
                                      //  rvViewOrder.setAdapter(adapter);
                                    } else {
                                        // Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }else if(selectedStatus.equals("canceled")){
                    firestore.collection("orders")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            name = document.getString("name");
                                            address = document.getString("address");
                                            para = document.getString("para");
                                            phone = document.getString("phone");
                                            status = document.getString("orderStatus");
                                            String id = document.getId();

                                            if(status.equals("canceled")) {
                                                list.add(new CustomerModel(name, address, para, phone, id));
                                            }
                                        }
                                        adapter.updateList(list);
                                        list.clear();
                                     //   rvViewOrder.setAdapter(adapter);
                                    } else {

                                    }
                                }
                            });
                }else if(selectedStatus.equals("delivered")){
                    firestore.collection("orders")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            name = document.getString("name");
                                            address = document.getString("address");
                                            para = document.getString("para");
                                            phone = document.getString("phone");
                                            status = document.getString("orderStatus");
                                            String id = document.getId();
                                            if(status.equals("delivered")) {
                                                list.add(new CustomerModel(name, address, para, phone, id));
                                            }

                                        }
                                        adapter.updateList(list);
                                        list.clear();
                                       // rvViewOrder.setAdapter(adapter);
                                    } else {
                                        // Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }
            }
        });
        firestore.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                 name = document.getString("name");
                                 address = document.getString("address");
                                 para = document.getString("para");
                                 phone = document.getString("phone");
                                 status = document.getString("orderStatus");
                                String id = document.getId();
                                if(status.equals("pending")) {
                                    list.add(new CustomerModel(name, address, para, phone, id));
                                }
                            }
                            adapter.updateList(list);
                            list.clear();
                        } else {
                           // Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCartListButtonClicked(int pos) {

    }
}