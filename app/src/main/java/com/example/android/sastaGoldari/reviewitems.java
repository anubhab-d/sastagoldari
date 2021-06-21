package com.example.android.sastaGoldari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class reviewitems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewitems);
        Button next = (Button) findViewById(R.id.next);
        Log.d("chk_list",MainActivity.cartList.get(0).getQty());
        int apha = getIntent().getIntExtra("a", 0);
        TextView list =(TextView)findViewById(R.id.list);
        list.setText(""+apha);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(reviewitems.this, address.class);
                startActivity(i);
            }
        });
    }
}
