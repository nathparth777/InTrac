package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Delete_Item extends AppCompatActivity {

    public static EditText BarId;
    Button Scan, Delete;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth Auth;
    String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__item);

        BarId = findViewById(R.id.id);
        Scan = findViewById(R.id.scan);
        Delete = findViewById(R.id.delete);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Auth = FirebaseAuth.getInstance();
        Uid = Auth.getUid();
        progressDialog = new ProgressDialog(Delete_Item.this);

        final Global global = (Global)getApplicationContext();

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Delete_Item.this,DeleteScanner.class));
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
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

                        //Timestamp for Date and Time
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        final String date = simpleDateFormat.format(cal.getTime());

                        firebaseFirestore.collection("Products").document(mBarId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    if (task.getResult().exists()){
                                        int value = Integer.parseInt(task.getResult().getString("flag"));
                                        final String mName = task.getResult().getString("Name");
                                        final String mWeight = task.getResult().getString("Weight");

                                        if (value==0){
                                            value=1;
                                            String val = Integer.toString(value);

                                            final Map<String, Object> flagval = new HashMap<>();
                                            flagval.put("flag", val);
                                            firebaseFirestore.collection("Products").document(mBarId).update(flagval).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){

                                                        firebaseFirestore.collection("Product by name").document(mName+" "+mWeight).update(flagval).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){

                                                                    Map<String, Object> prolog = new HashMap<>();
                                                                    prolog.put("Delete_By", global.getEamilId());

                                                                    firebaseFirestore.collection("Product log").document(mBarId).update(prolog).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            if (task.isSuccessful()){

                                                                                Map<String, Object> worker = new HashMap<>();
                                                                                worker.put("Item_Deleted",mBarId);
                                                                                worker.put("Date",date);

                                                                                //uploading hashmap in firebase firestore
                                                                                firebaseFirestore.collection("Worker log").document(global.getEamilId()).collection("AddDelete").document(date).set(worker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        progressDialog.dismiss();
                                                                                        Toast.makeText(Delete_Item.this, "Deleted", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {
                                                                                        progressDialog.dismiss();
                                                                                        Toast.makeText(Delete_Item.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });
                                                                            }else {
                                                                                Toast.makeText(Delete_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }



                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(Delete_Item.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                }else {
                                                                    Toast.makeText(Delete_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Delete_Item.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }else {
                                                        Toast.makeText(Delete_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Delete_Item.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Delete_Item.this, "No record found", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(Delete_Item.this, "No record found", Toast.LENGTH_SHORT).show();
                                    }
                                }else
                                {
                                    Toast.makeText(Delete_Item.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Delete_Item.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        BarId.setError("Null");
                        BarId.setHint("Please enter product id");
                    }
                }else{
                    Toast.makeText(Delete_Item.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
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