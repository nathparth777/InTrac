package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddDelete_Details2 extends AppCompatActivity {

    TextView Date, Add, Delete, Add1, Del1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete__details2);

        Date = findViewById(R.id.date);
        Add = findViewById(R.id.padd);
        Delete = findViewById(R.id.pdel);
        Add1 = findViewById(R.id.padd1);
        Del1 = findViewById(R.id.pdel1);

        Date.setText(getIntent().getStringExtra("Date"));

        if (getIntent().getStringExtra("Add")==null){
            Add1.setVisibility(View.GONE);
            Add.setVisibility(View.GONE);
        }else{
            Add.setText(getIntent().getStringExtra("Add"));
        }
        if (getIntent().getStringExtra("Del")==null){
            Del1.setVisibility(View.GONE);
            Delete.setVisibility(View.GONE);
        }else{
            Delete.setText(getIntent().getStringExtra("Del"));
        }

    }
}