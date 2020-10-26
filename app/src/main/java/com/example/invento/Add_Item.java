package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.invento.auth.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Add_Item extends AppCompatActivity {

    private static final int CAMERA = 1;
    public static EditText BarId, Name, Weight, MRP, Totalitems, Manufacture, Expiry;
    Button Scan, Additem;
    ImageButton ManuButton, ExpButton;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    //private FirebaseAuth Auth;
    String Uid, value;
    int year;
    int month;
    int day;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);

        BarId = findViewById(R.id.id);
        Name = findViewById(R.id.name);
        Weight = findViewById(R.id.weight);
        MRP = findViewById(R.id.mrp);
        Totalitems = findViewById(R.id.total);
        Manufacture = findViewById(R.id.manufacture);
        ManuButton = findViewById(R.id.manufactureButton);
        ExpButton = findViewById(R.id.expiryButton);
        Expiry = findViewById(R.id.expiry);
        Scan = findViewById(R.id.scan);
        Additem = findViewById(R.id.additem);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //Auth = FirebaseAuth.getInstance();
        //Uid = Auth.getUid();

        progressDialog = new ProgressDialog(Add_Item.this);

        final Calendar calendar = Calendar.getInstance();

        if(getIntent().getStringExtra("id") != null){
            value = getIntent().getStringExtra("id");
            BarId.setText(value);
        }

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Add_Item.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(Add_Item.this,Scanner.class));

                }else{
                    ActivityCompat.requestPermissions(Add_Item.this,new String[]{Manifest.permission.CAMERA}, CAMERA);
                }

            }
        });

        ManuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Calendar calendar = Calendar.getInstance();
               year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                   DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Item.this, new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                           Manufacture.setText(i2 +"/"+ (i1+1) +"/"+ i);
                       }
                   },year, month, day);
                   datePickerDialog.show();

            }
        });

        ExpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Item.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Expiry.setText(i2 +"/"+ (i1+1) +"/"+ i);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        final Global global = (Global)getApplicationContext();

        Additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To check Connectivity
                boolean connect = isNetworkAvailable();
                if (connect==true){
                    //Timestamp for Date and Time
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    final String date = simpleDateFormat.format(cal.getTime());

                    final String mBarId = BarId.getText().toString();
                    final String mName = Name.getText().toString();
                    final String mWeight = Weight.getText().toString();
                    final String mMRP = MRP.getText().toString();
                    final String mToatal = Totalitems.getText().toString();
                    final String mManufacture = Manufacture.getText().toString();
                    final String mExpiry = Expiry.getText().toString();


                    if (!TextUtils.isEmpty(mBarId) && !TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mWeight) && !TextUtils.isEmpty(mMRP) && !TextUtils.isEmpty(mToatal) ){
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
                                        progressDialog.dismiss();
                                        Toast.makeText(global, "Product already exists ", Toast.LENGTH_SHORT).show();
                                    }else{
                                        final Map<String, Object> ItemsVal = new HashMap<>();
                                        ItemsVal.put("ID", mBarId);
                                        ItemsVal.put("flag", "0");
                                        ItemsVal.put("Name", mName);
                                        ItemsVal.put("Date_of_Adding", date);
                                        ItemsVal.put("Weight", mWeight);
                                        ItemsVal.put("MRP", mMRP);
                                        ItemsVal.put("Total_Items", mToatal);
                                        ItemsVal.put("Add_By", global.getEamilId());
                                        ItemsVal.put("Manufacture_Date", mManufacture);
                                        ItemsVal.put("Expiry_Date", mExpiry);

                                        //uploading hashmap in firebase firestore
                                        firebaseFirestore.collection("Products").document(mBarId).set(ItemsVal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    Map<String, Object> ItemsValue = new HashMap<>();
                                                    ItemsValue.put("Add_By", global.getEamilId());

                                                    //uploading hashmap in firebase firestore
                                                    firebaseFirestore.collection("Product log").document(mBarId).set(ItemsValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                final Map<String, Object> worker = new HashMap<>();
                                                                worker.put("Item_Added", mBarId);
                                                                worker.put("Date", date);

                                                                //uploading hashmap in firebase firestore
                                                                firebaseFirestore.collection("Worker log").document(global.getEamilId()).collection("AddDelete").document(date).set(worker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()){

                                                                            Map<String, Object> proname = new HashMap<>();
                                                                            proname.put("ID", mBarId);
                                                                            proname.put("flag", "0");
                                                                            proname.put("Name", mName);
                                                                            proname.put("Subname", mName+" "+mWeight);
                                                                            proname.put("NameSL", (mName+" "+mWeight).toLowerCase());
                                                                            proname.put("Date_of_Adding", date);
                                                                            proname.put("Weight", mWeight);
                                                                            proname.put("MRP", mMRP);
                                                                            proname.put("Total_Items", mToatal);
                                                                            proname.put("Add_By", global.getEamilId());
                                                                            proname.put("Manufacture_Date", mManufacture);
                                                                            proname.put("Expiry_Date", mExpiry);
                                                                            firebaseFirestore.collection("Product by name").document(mName+" "+mWeight).set(proname).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()){
                                                                                        progressDialog.dismiss();
                                                                                        Toast.makeText(Add_Item.this,"Product added Successfully",Toast.LENGTH_SHORT).show();
                                                                                        startActivity(new Intent(Add_Item.this,MainActivity.class));
                                                                                        finish();
                                                                                    }else
                                                                                    {
                                                                                        progressDialog.dismiss();
                                                                                        Toast.makeText(Add_Item.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    progressDialog.dismiss();
                                                                                    Toast.makeText(Add_Item.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                        }else
                                                                        {
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(Add_Item.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(Add_Item.this,"Database Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Add_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(Add_Item.this,"Database Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Add_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Add_Item.this,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }else {
                                    Toast.makeText(Add_Item.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add_Item.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        if (TextUtils.isEmpty(mBarId)){
                            BarId.setError("Id is required!");
                        }
                        if (TextUtils.isEmpty(mName)){
                            Name.setError("Name is required!");
                        }
                        if (TextUtils.isEmpty(mWeight)){
                            Weight.setError("Weight is required!");
                        }
                        if (TextUtils.isEmpty(mMRP)){
                            MRP.setError("MRP is required!");
                        }
                        if (TextUtils.isEmpty(mToatal)){
                            Totalitems.setError("Total items is required!");
                        }
                    }
                }else {
                    Toast.makeText(Add_Item.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                }


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