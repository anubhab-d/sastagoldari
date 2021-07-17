package com.example.android.sastaGoldari.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.sastaGoldari.R;

public class Welcome extends AppCompatActivity {
    private LinearLayout llQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        llQuery = findViewById(R.id.llQuery);
        Button customer = (Button) findViewById(R.id.user);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Welcome.this, MainActivity.class);
                startActivity(i);
            }
        });

        ImageView admin = (ImageView) findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(Welcome.this, logadmin.class);
                startActivity(j);
            }
        });
        llQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp("+919432519573");
            }
        });
    }
    private void openWhatsapp(String num) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.whatsapp");
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+num+"&text="));
        if (Welcome.this.getPackageManager().resolveActivity(intent, 0) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(Welcome.this, "Please install whatsapp", Toast.LENGTH_SHORT).show();
        }
    }
}
