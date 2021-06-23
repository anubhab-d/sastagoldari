package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.adapter.ViewOrderAdapter;
import com.example.android.sastaGoldari.interfaces.OnViewOrderListBtnClicked;
import com.example.android.sastaGoldari.model.CartModel;
import com.example.android.sastaGoldari.model.CustomerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class vieworder extends AppCompatActivity implements OnViewOrderListBtnClicked {
FirebaseFirestore firestore;
    String name;
    String address;
    String para;
    String phone;
    ArrayList<CustomerModel> list = new ArrayList<>();
    List<CartModel> cartList = new ArrayList<>();
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
                                 name = document.getString("name");
                                 address = document.getString("address");
                                 para = document.getString("para");
                                 phone = document.getString("phone");
//                                List<String> itemName = (List<String>) document.get("orderItemNames");
//                                List<String> itemPrice = (List<String>) document.get("orderItemPrice");
//                                List<String> itemQty = (List<String>) document.get("orderItemQty");
//                                List<String> itemUnit = (List<String>) document.get("orderItemUnit");
//                                Log.d("chk_list",Integer.toString(itemName.size()));
//                                for(int i =0; i<itemName.size();i++){
//                                    cartList.add(new CartModel(itemName.get(i),itemPrice.get(i),itemUnit.get(i),itemQty.get(i)));
//                                }
                                String id = document.getId();
                                list.add(new CustomerModel(name,address,para,phone,id));
                               // cartList.clear();
                            }
                           // Log.d("chk_list2",Integer.toString(list.get(1).getCartList().size()));
                            adapter.updateList(list);
                            rvViewOrder.setAdapter(adapter);
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