package com.example.android.sastaGoldari.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.sastaGoldari.R;

public class adminpanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);
        TextView add = (TextView) findViewById(R.id.textView6);
        TextView edit = (TextView) findViewById(R.id.textView8);
        TextView delete = (TextView) findViewById(R.id.textView7);
        TextView vieworder = (TextView) findViewById(R.id.textView9);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(adminpanel.this, additem.class);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(adminpanel.this, edititem.class);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(adminpanel.this, deleteitem.class);
                startActivity(i);
            }
        });

        vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(adminpanel.this, com.example.android.sastaGoldari.activities.vieworder.class);
                startActivity(i);
            }
        });
    }
}
