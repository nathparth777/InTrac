package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invento.auth.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA = 1;
    Button Add, Logout, Display, Delete, Products, Generate;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout msgLayout;
    TextView adminmsg1, adminmsg2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Matching Ids
        Add = findViewById(R.id.add);
        Logout = findViewById(R.id.logout);
        Display = findViewById(R.id.display);
        Generate = findViewById(R.id.Generate);
        Delete = findViewById(R.id.delete);
        Products = findViewById(R.id.product);
        msgLayout = findViewById(R.id.msglayout);
        adminmsg1 = findViewById(R.id.adminmsg1);
        adminmsg2 = findViewById(R.id.adminmsg2);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        firebaseFirestore.collection("Admin Message").document("message").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        if (task.getResult().getString("Message")!=null){
                            msgLayout.setVisibility(View.VISIBLE);
                            adminmsg1.setText(task.getResult().getString("Message"));
                            adminmsg2.setText(task.getResult().getString("Date"));
                        }else{
                            msgLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, CAMERA);
        }

        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Display_details.class));
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add_Item.class));
            }
        });



        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Delete_Item.class));
            }
        });

        Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Products.class));
            }
        });

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Generate_QR.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        msgLayout = findViewById(R.id.msglayout);

        adminmsg1 = findViewById(R.id.adminmsg1);
        adminmsg2 = findViewById(R.id.adminmsg2);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Admin Message").document("message").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        if (task.getResult().getString("Message")!=null){
                            msgLayout.setVisibility(View.VISIBLE);
                            adminmsg1.setText(task.getResult().getString("Message"));
                            adminmsg2.setText(task.getResult().getString("Date"));
                        }else{
                            msgLayout.setVisibility(View.GONE);
                        }

                    }
                }
            }
        });
    }
}