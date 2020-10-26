package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminDisplay_Products extends AppCompatActivity {

    private static final int CAMERA = 1;
    Button Scan, Search, ProductLog;
    public static EditText BarId;
    TextView Name, Weight, MRP, ID, Quantity, Date, Del, Manufacture, Expiry;
    ScrollView Record;
    LinearLayout NoRecord;
    private FirebaseFirestore firebaseFirestore;
    //private FirebaseAuth Auth;
    //String Uid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display__products);

        BarId = findViewById(R.id.id);
        Name = findViewById(R.id.name);
        Weight = findViewById(R.id.weight);
        MRP = findViewById(R.id.mrp);
        ID = findViewById(R.id.id2);
        Quantity = findViewById(R.id.quantity);
        Date = findViewById(R.id.date);
        Manufacture = findViewById(R.id.manufacture);
        Expiry = findViewById(R.id.expiry);
        Scan = findViewById(R.id.scan);
        Search = findViewById(R.id.search);
        ProductLog = findViewById(R.id.history);
        //AddProduct = findViewById(R.id.addproduct);
        Record = findViewById(R.id.record);
        Del = findViewById(R.id.Del);

        NoRecord = findViewById(R.id.norecord);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //Auth = FirebaseAuth.getInstance();
        //Uid = Auth.getUid();
        progressDialog = new ProgressDialog(AdminDisplay_Products.this);

        final Global global = (Global)getApplicationContext();


        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(AdminDisplay_Products.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(AdminDisplay_Products.this,Admin_Scanner.class));

                }else{
                    ActivityCompat.requestPermissions(AdminDisplay_Products.this,new String[]{Manifest.permission.CAMERA}, CAMERA);
                }

            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To check Connectivity
                boolean connect = isNetworkAvailable();
                if (connect==true){
                    final String mBarId = BarId.getText().toString();

                    if (!TextUtils.isEmpty(mBarId)){
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );

                        firebaseFirestore.collection("Products").document(mBarId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    if (task.getResult().exists()){
                                        int value = Integer.parseInt(task.getResult().getString("flag"));

                                        if (value==1){
                                            Del.setVisibility(View.VISIBLE);

                                        }else{
                                            Del.setVisibility(View.GONE);
                                        }
                                        progressDialog.dismiss();
                                        NoRecord.setVisibility(View.GONE);
                                        Record.setVisibility(View.VISIBLE);

                                        Name.setText(task.getResult().getString("Name"));
                                        Weight.setText(task.getResult().getString("Weight"));
                                        MRP.setText(task.getResult().getString("MRP"));
                                        ID.setText(task.getResult().getString("ID"));
                                        Quantity.setText(task.getResult().getString("Total_Items"));
                                        Date.setText(task.getResult().getString("Date_of_Adding"));
                                        Manufacture.setText(task.getResult().getString("Manufacture_Date"));
                                        Expiry.setText(task.getResult().getString("Expiry_Date"));
                                        //Addby.setText(task.getResult().getString("Add_By"));
                                        global.setID(ID.getText().toString());







                                    }else{
                                        progressDialog.dismiss();
                                        Record.setVisibility(View.GONE);
                                        NoRecord.setVisibility(View.VISIBLE);
                                        //AddProduct.setVisibility(View.VISIBLE);
                                    }

                                }else
                                {
                                    Toast.makeText(AdminDisplay_Products.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(AdminDisplay_Products.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }else{
                        Toast.makeText(AdminDisplay_Products.this, "Please enter id or Scan Barcode/QR", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AdminDisplay_Products.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                }



            }
        });


        ProductLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Global global = (Global)getApplicationContext();
                final String id = global.getID();
                firebaseFirestore.collection("Product log").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().exists()){
                                String addby = task.getResult().getString("Add_By");
                                String deleteby = task.getResult().getString("Delete_By");

                                Intent intent = new Intent(AdminDisplay_Products.this,ProductHistory.class);
                                intent.putExtra("barid",id);
                                intent.putExtra("add",addby);
                                intent.putExtra("delete",deleteby);
                                startActivity(intent);
                            }else{
                                Toast.makeText(AdminDisplay_Products.this, "No History", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(AdminDisplay_Products.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminDisplay_Products.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}