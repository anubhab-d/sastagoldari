package com.example.android.sastaGoldari.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.sastaGoldari.R;
import com.example.android.sastaGoldari.adapter.CartListAdapter;
import com.example.android.sastaGoldari.interfaces.OnCartListRemoveBtnClicked;
import com.example.android.sastaGoldari.model.CartModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class reviewitems extends AppCompatActivity implements OnCartListRemoveBtnClicked {
    ArrayList<CartModel> cartList = new ArrayList<>(MainActivity.cartList);
    CartListAdapter adapter = new CartListAdapter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewitems);
        FloatingActionButton next = findViewById(R.id.next);
        RecyclerView rvCartItem = findViewById(R.id.rvCartItem);

        rvCartItem.setLayoutManager(new LinearLayoutManager(this));
        rvCartItem.setAdapter(adapter);
        adapter.updateList(cartList);
        int itemCount = cartList.size();
        TextView n = (TextView) findViewById(R.id.tvCartItemCnt);
        if(itemCount>1){
            n.setText(""+itemCount+" items found");
        }
        else{
            n.setText(""+itemCount+" item found");
        }

       // Log.d("chk_list",MainActivity.cartList.get(0).getQty());
        int apha = getIntent().getIntExtra("a", 0);
      //  TextView list =(TextView)findViewById(R.id.list);
      //  list.setText(""+apha);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(reviewitems.this, address.class);
                MainActivity.cartList.clear();
                MainActivity.cartList.addAll(cartList);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        MainActivity.cartList.clear();
        super.onBackPressed();
    }

    @Override
    public void onCartListButtonClicked(int pos) {
        cartList.remove(pos);
        adapter.updateList(cartList);
    }
}
