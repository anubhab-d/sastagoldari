package com.example.android.sastaGoldari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.android.sastaGoldari.adapter.ItemAdapter;
import com.example.android.sastaGoldari.model.SellingItems;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  ArrayList<SellingItems> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
list.add(new SellingItems("Ahgvhgvhgvgvhghgvhhgvhg","320"));
list.add(new SellingItems("B","320"));
list.add(new SellingItems("c","320"));
list.add(new SellingItems("D","320"));
list.add(new SellingItems("E","320"));
list.add(new SellingItems("F","320"));
list.add(new SellingItems("A","321"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","345"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));
list.add(new SellingItems("A","320"));

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
        RecyclerView rvItems =findViewById(R.id.rvItems);
        ItemAdapter adapter = new ItemAdapter();
        rvItems.setLayoutManager(new GridLayoutManager(this,2));
        adapter.updateList(list);
        rvItems.setAdapter(adapter);

        ImageView cart = (ImageView) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, reviewitems.class);
                startActivity(i);
            }
        });
    }
}
