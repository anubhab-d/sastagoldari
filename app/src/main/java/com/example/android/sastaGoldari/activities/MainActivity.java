package com.example.android.sastaGoldari.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.adapter.ItemAdapter;
import com.example.android.sastaGoldari.interfaces.OnButtonClicked;
import com.example.android.sastaGoldari.model.CartModel;
import com.example.android.sastaGoldari.model.SellingItems;
import com.example.android.sastaGoldari.utils.ConstCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnButtonClicked {
  ArrayList<SellingItems> list = new ArrayList<>();
  public static ArrayList<CartModel> cartList = new ArrayList<>();
  FirebaseFirestore db;
    TextView txtNoti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvItems =findViewById(R.id.rvItems);
        ItemAdapter adapter = new ItemAdapter(this,this);
         txtNoti = findViewById(R.id.txtNoti);
        db = FirebaseFirestore.getInstance();
        db.collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                String name = document.getString("name");
                                String price = document.getString("price");
                                String unit = document.getString("unit");
                                String imgL = document.getString("imgL");
                                String id = document.getId();
                                list.add(new SellingItems(name,price,unit,id,imgL));
                                rvItems.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                                adapter.updateList(list);
                                rvItems.setAdapter(adapter);
                            }
                        } else {
                            ConstCode.showToast(MainActivity.this,"Sorry Can't Load");
                        }
                    }
                });

//        Button plus = (Button) findViewById(R.id.plus);
//        Button minus = (Button) findViewById(R.id.minus);
//        plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView text = (TextView) findViewById(R.id.txtQuantity);
//                TextView unit = (TextView) findViewById(R.id.txtUnit);
//
//                double qtykg = Double.parseDouble(text.getText().toString());
//                if(unit.getText().toString().equals("kg")){
//                    String stringdouble= Double.toString(qtykg+0.5);
//                    text.setText(stringdouble);
//                }
//                else if(unit.getText().toString().equals("qty")){
//                    String stringdouble= Double.toString(qtykg+1.0);
//                    text.setText(stringdouble);
//                }
//
//            }
//        });
//        minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView text = (TextView) findViewById(R.id.txtQuantity);
//                double qtykg = Double.parseDouble(text.getText().toString());
//                TextView unit = (TextView) findViewById(R.id.txtUnit);
//                if(qtykg>0.0){
//                    if(unit.getText().toString().equals("kg")){
//                        String stringdouble= Double.toString(qtykg-0.5);
//                        text.setText(stringdouble);
//                    }
//                    else if(unit.getText().toString().equals("qty")){
//                        String stringdouble= Double.toString(qtykg-1.0);
//                        text.setText(stringdouble);
//                    }
//                }
//
//            }
//        });


        ImageView cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, reviewitems.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onAddButtonClicked(TextView name, TextView price, TextView unit, TextView qty) {
        cartList.add(new CartModel(name.getText().toString()
                ,price.getText().toString()
                ,unit.getText().toString()
                ,qty.getText().toString()));
        txtNoti.setText(Integer.toString(cartList.size()));
    }
}
