package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

public class Products_Details extends AppCompatActivity {

    TextView Name, Weight, MRP, ID, Quantity, Date, Manufacture, Expiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products__details);


        Name = findViewById(R.id.name);
        Weight = findViewById(R.id.weight);
        MRP = findViewById(R.id.mrp);
        ID = findViewById(R.id.id2);
        Quantity = findViewById(R.id.quantity);
        Date = findViewById(R.id.date);
        Manufacture = findViewById(R.id.manufacture);
        Expiry = findViewById(R.id.expiry);

        Name.setText(getIntent().getStringExtra("Name"));
        Weight.setText(getIntent().getStringExtra("Weight"));
        MRP.setText(getIntent().getStringExtra("MRP"));
        ID.setText(getIntent().getStringExtra("ID"));
        Quantity.setText(getIntent().getStringExtra("Quantity"));
        Date.setText(getIntent().getStringExtra("Date"));
        Manufacture.setText(getIntent().getStringExtra("Manufacture"));
        Expiry.setText(getIntent().getStringExtra("Expiry"));
    }
}