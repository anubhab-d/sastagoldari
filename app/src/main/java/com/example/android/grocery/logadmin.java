package com.example.android.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class logadmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logadmin);

        Button login =(Button) findViewById(R.id.button3);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.editText);
                EditText password = (EditText) findViewById(R.id.editText2);

                if(username.getText().toString().equals("user") && password.getText().toString().equals("pass")){
                    Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(logadmin.this, adminpanel.class);
                    startActivity(i);


                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect username/password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
