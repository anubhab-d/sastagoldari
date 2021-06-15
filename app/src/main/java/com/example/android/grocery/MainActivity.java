package com.example.android.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button plus = (Button) findViewById(R.id.plus);
        Button minus = (Button) findViewById(R.id.minus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.text1);
                TextView unit = (TextView) findViewById(R.id.unit);

                double qtykg = Double.parseDouble(text.getText().toString());
                if(unit.getText().toString().equals("kg")){
                    String stringdouble= Double.toString(qtykg+0.5);
                    text.setText(stringdouble);
                }
                else if(unit.getText().toString().equals("qty")){
                    String stringdouble= Double.toString(qtykg+1.0);
                    text.setText(stringdouble);
                }

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) findViewById(R.id.text1);
                double qtykg = Double.parseDouble(text.getText().toString());
                TextView unit = (TextView) findViewById(R.id.unit);
                if(qtykg>0.0){
                    if(unit.getText().toString().equals("kg")){
                        String stringdouble= Double.toString(qtykg-0.5);
                        text.setText(stringdouble);
                    }
                    else if(unit.getText().toString().equals("qty")){
                        String stringdouble= Double.toString(qtykg-1.0);
                        text.setText(stringdouble);
                    }
                }

            }
        });




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
