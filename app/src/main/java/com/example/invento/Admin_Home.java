package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.invento.auth.Login;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Home extends AppCompatActivity {
    Button AddEmployee, Logout, Employees, All_Products, Display, Message;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__home);

        AddEmployee = findViewById(R.id.addnew);
        Logout = findViewById(R.id.signout);
        Employees = findViewById(R.id.employee);
        mAuth = FirebaseAuth.getInstance();
        Display = findViewById(R.id.display);
        All_Products = findViewById(R.id.allproducts);
        Message = findViewById(R.id.message);

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home.this,Message.class));
            }
        });

        AddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home.this,Add_Employee.class));
            }
        });

        Employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home.this,Employees.class));
            }
        });

        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home.this,AdminDisplay_Products.class));
            }
        });

        All_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Home.this,Products.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(Admin_Home.this, Login.class));
                finish();
            }
        });
    }
}