package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class searchmodify_details2 extends AppCompatActivity {
    TextView amrp,aname,aquantity,bmrp,bname,bquantity,amrp1,aname1,aquantity1,bmrp1,bname1,bquantity1,date,activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmodify_details2);

        amrp=findViewById(R.id.amrp);
        aname=findViewById(R.id.aname);
        aquantity=findViewById(R.id.aquantity);
        bmrp=findViewById(R.id.bmrp);
        bname=findViewById(R.id.bname);
        bquantity=findViewById(R.id.bquantity);
        amrp1=findViewById(R.id.amrp1);
        aname1=findViewById(R.id.aname1);
        aquantity1=findViewById(R.id.aquantity1);
        bquantity1=findViewById(R.id.bquantity1);
        bname1=findViewById(R.id.bname1);
        bmrp1=findViewById(R.id.bmrp1);
        activity=findViewById(R.id.activity);
        date=findViewById(R.id.date);

        activity.setText(getIntent().getStringExtra("Activity"));
        date.setText(getIntent().getStringExtra("Date"));


        if (getIntent().getStringExtra("amrp")==null)
        {
            amrp.setVisibility(View.GONE);
            aname.setVisibility(View.GONE);
            aquantity.setVisibility(View.GONE);
            amrp1.setVisibility(View.GONE);
            aname1.setVisibility(View.GONE);
            aquantity1.setVisibility(View.GONE);
            bmrp.setVisibility(View.GONE);
            bmrp1.setVisibility(View.GONE);
            bname.setVisibility(View.GONE);
            bquantity.setVisibility(View.GONE);
            bquantity1.setVisibility(View.GONE);
            bname1.setVisibility(View.GONE);


        }else
        {
            amrp.setText(getIntent().getStringExtra("amrp"));
            aname.setText(getIntent().getStringExtra("aname"));
            aquantity.setText(getIntent().getStringExtra("aquantity"));
            bmrp.setText(getIntent().getStringExtra("bmrp"));
            bname.setText(getIntent().getStringExtra("bname"));
            bquantity.setText(getIntent().getStringExtra("bquantity"));
        }



    }
}