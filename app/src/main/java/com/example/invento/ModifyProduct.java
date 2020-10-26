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
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class ModifyProduct extends AppCompatActivity {

    //private static final int CAMERA = 1;
    //public static EditText BarId;
    EditText Name, MRP, Updateval;
    TextView Weight, Quantity, Id;
    Button Update;
    Spinner spinner;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    String[] mod = {"Add Product", "Sell Product"};
    String value;
    //RelativeLayout record;
    //LinearLayout norecord;
    //private FirebaseAuth Auth;
    //String Uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_product);

        //BarId = findViewById(R.id.id);
        Name = findViewById(R.id.name);
        MRP = findViewById(R.id.mrp);
        Updateval = findViewById(R.id.updateval);
        Weight = findViewById(R.id.weight);
        Quantity = findViewById(R.id.quantity);
        Id = findViewById(R.id.Id);
        Update = findViewById(R.id.update);
        spinner = findViewById(R.id.spinner);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //record = findViewById(R.id.record);
        //norecord = findViewById(R.id.norecord);
        //Auth = FirebaseAuth.getInstance();
        //Uid = Auth.getUid();
        progressDialog = new ProgressDialog(ModifyProduct.this);

        final String barid = getIntent().getStringExtra("barid");
        Id.setText(barid);

        final Global global = (Global)getApplicationContext();

        ArrayAdapter aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mod);
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value = mod[i];
                Global global = (Global)getApplicationContext();
                global.setData(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ModifyProduct.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(ModifyProduct.this,UpdateScanner.class));

                }else{
                    ActivityCompat.requestPermissions(ModifyProduct.this,new String[]{Manifest.permission.CAMERA}, CAMERA);
                }
            }
        });*/

        //For Product details
        boolean connect = isNetworkAvailable();
        if (connect==true){
            //final String mBarId = BarId.getText().toString();
            if (!TextUtils.isEmpty(barid)){
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                firebaseFirestore.collection("Products").document(barid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){


                            int value = Integer.parseInt(task.getResult().getString("flag"));

                            if (value==0){
                                progressDialog.dismiss();
                                //record.setVisibility(View.VISIBLE);
                                Name.setText(task.getResult().getString("Name"));
                                Weight.setText(task.getResult().getString("Weight"));
                                MRP.setText(task.getResult().getString("MRP"));
                                Quantity.setText(task.getResult().getString("Total_Items"));
                                String addby = task.getResult().getString("Add_By");
                                String DateAdding = task.getResult().getString("Date_of_Adding");
                                String flag = task.getResult().getString("flag");
                                String manu = task.getResult().getString("Manufacture_Date");
                                String exp = task.getResult().getString("Expiry_Date");

                                global.setMaufacture(manu);
                                global.setExpiry(exp);
                                global.setID(barid);
                                global.setName(task.getResult().getString("Name"));
                                global.setMrp(task.getResult().getString("MRP"));
                                global.setAddby(addby);
                                global.setDateAdding(DateAdding);
                                global.setFlag(flag);
                            }else{
                                progressDialog.dismiss();
                                //norecord.setVisibility(View.VISIBLE);
                                Toast.makeText(ModifyProduct.this, "Deleted Product", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(ModifyProduct.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(ModifyProduct.this, "Please enter Id", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(ModifyProduct.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
        }

        /*Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //record.setVisibility(View.GONE);
                //norecord.setVisibility(View.GONE);
                //To check Connectivity
                boolean connect = isNetworkAvailable();
                if (connect==true){
                    //final String mBarId = BarId.getText().toString();
                    if (!TextUtils.isEmpty(barid)){
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );
                        firebaseFirestore.collection("Products").document(barid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){


                                        int value = Integer.parseInt(task.getResult().getString("flag"));

                                        if (value==0){
                                            progressDialog.dismiss();
                                            //record.setVisibility(View.VISIBLE);
                                            Name.setText(task.getResult().getString("Name"));
                                            Weight.setText(task.getResult().getString("Weight"));
                                            MRP.setText(task.getResult().getString("MRP"));
                                            Quantity.setText(task.getResult().getString("Total_Items"));
                                            String addby = task.getResult().getString("Add_By");
                                            String DateAdding = task.getResult().getString("Date_of_Adding");
                                            String flag = task.getResult().getString("flag");

                                            global.setID(barid);
                                            global.setName(task.getResult().getString("Name"));
                                            global.setMrp(task.getResult().getString("MRP"));
                                            global.setAddby(addby);
                                            global.setDateAdding(DateAdding);
                                            global.setFlag(flag);
                                        }else{
                                            progressDialog.dismiss();
                                            //norecord.setVisibility(View.VISIBLE);
                                            Toast.makeText(ModifyProduct.this, "Deleted Product", Toast.LENGTH_SHORT).show();
                                        }
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(ModifyProduct.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(ModifyProduct.this, "Please enter Id", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ModifyProduct.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                }




            }
        });*/

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //To check Connectivity
                boolean connect = isNetworkAvailable();
                if (connect==true){
                    final String mName = Name.getText().toString();
                    final String mMRP = MRP.getText().toString();
                    String mUpdate = Updateval.getText().toString();
                    final String mQuantity = Quantity.getText().toString();
                    final String mWeight = Weight.getText().toString();

                    if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mMRP) && !TextUtils.isEmpty(mUpdate)){
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(
                                android.R.color.transparent
                        );


                        value = global.getData();
                        int value1 = Integer.parseInt(mQuantity);
                        int value2 = Integer.parseInt(mUpdate);
                        if (value=="Remove" && value1<value2){
                            progressDialog.dismiss();
                            Toast.makeText(ModifyProduct.this, "Removing value is greater ", Toast.LENGTH_SHORT).show();
                            Updateval.setError("Please enter proper value");
                        }else{
                            if (value=="Add Product"){
                                int val1 = Integer.parseInt(mQuantity);
                                int val2 = Integer.parseInt(mUpdate);
                                int val = val1+val2;
                                global.setVal(val);

                            }else if (value=="Sell Product"){
                                int val1 = Integer.parseInt(mQuantity);
                                int val2 = Integer.parseInt(mUpdate);
                                int val = val1-val2;
                                global.setVal(val);
                            }

                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                            final String date = simpleDateFormat.format(cal.getTime());

                            final String titems = Integer.toString(global.getVal());
                            final Map<String, Object> UpdatedVal = new HashMap<>();
                            UpdatedVal.put("Name", mName);
                            UpdatedVal.put("MRP", mMRP);
                            UpdatedVal.put("Total_Items", titems);
                            UpdatedVal.put("Weight", mWeight);

                            //For Products
                            firebaseFirestore.collection("Products").document(global.getID()).update(UpdatedVal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        //For Product by Name
                                        firebaseFirestore.collection("Product by name").document(global.getName()+" "+mWeight).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){

                                                    Map<String, Object> setUpdatedVal = new HashMap<>();
                                                    setUpdatedVal.put("Name", mName);
                                                    setUpdatedVal.put("NameSL", (mName+" "+mWeight).toLowerCase());
                                                    setUpdatedVal.put("Subname", mName+" "+mWeight);
                                                    setUpdatedVal.put("ID", global.getID());
                                                    setUpdatedVal.put("MRP", mMRP);
                                                    setUpdatedVal.put("Total_Items", titems);
                                                    setUpdatedVal.put("Weight", mWeight);
                                                    setUpdatedVal.put("Add_By", global.getAddby());
                                                    setUpdatedVal.put("Date_of_Adding", global.getDateAdding());
                                                    setUpdatedVal.put("flag", global.getFlag());
                                                    setUpdatedVal.put("Manufacture_Date", global.getMaufacture());
                                                    setUpdatedVal.put("Expiry_Date", global.getExpiry());
                                                    firebaseFirestore.collection("Product by name").document(mName+" "+mWeight).set(setUpdatedVal).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                //For Product log
                                                                Map<String, Object> prolog = new HashMap<>();
                                                                prolog.put("Modify_by", global.getEamilId());
                                                                prolog.put("Before_modify_Name", global.getName());
                                                                prolog.put("Before_modify_MRP", global.getMrp());
                                                                prolog.put("Before_modify_Quantity", mQuantity);
                                                                prolog.put("After_modify_Name", mName);
                                                                prolog.put("After_modify_MRP", mMRP);
                                                                prolog.put("After_modify_Quantity", titems);
                                                                prolog.put("Date_of_Modify", date);

                                                                firebaseFirestore.collection("Product log").document(global.getID()).collection(global.getID()).document(date).set(prolog).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()){
                                                                            //For Worker log
                                                                            Map<String, Object> wrolog = new HashMap<>();
                                                                            wrolog.put("Activity", "Modify");
                                                                            wrolog.put("Before_modify_Name", global.getName());
                                                                            wrolog.put("Before_modify_MRP", global.getMrp());
                                                                            wrolog.put("Before_modify_Quantity", mQuantity);
                                                                            wrolog.put("After_modify_Name", mName);
                                                                            wrolog.put("After_modify_MRP", mMRP);
                                                                            wrolog.put("After_modify_Quantity", titems);
                                                                            wrolog.put("Date", date);


                                                                            firebaseFirestore.collection("Worker log").document(global.getEamilId()).collection("SearchModify").document(global.getID()).collection("Activity").document(date).set(wrolog).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    progressDialog.dismiss();
                                                                                    Toast.makeText(ModifyProduct.this, "Modify Successfully", Toast.LENGTH_SHORT).show();
                                                                                    startActivity(new Intent(ModifyProduct.this,MainActivity.class));
                                                                                    finish();
                                                                                }
                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    progressDialog.dismiss();
                                                                                    Toast.makeText(ModifyProduct.this, "Modify Unsuccessfully4", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }else {
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }


                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressDialog.dismiss();
                                                                        Toast.makeText(ModifyProduct.this, "Modify Unsuccessfully3", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            }else{
                                                                progressDialog.dismiss();
                                                                Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });
                                                }else{
                                                    Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }



                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(ModifyProduct.this, "Modify Unsuccessfully2", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ModifyProduct.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ModifyProduct.this, "Modify Unsuccessfully1", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }


                    }else{

                        if (TextUtils.isEmpty(mName)){
                            Name.setError("Name is Required");
                            Name.setHint("Please enter Product name");
                        }
                        if (TextUtils.isEmpty(mMRP)){
                            MRP.setError("MRP is Required");
                            MRP.setHint("Please enter Product MRP");
                        }
                        if (TextUtils.isEmpty(mUpdate)){
                            Updateval.setError("Value is Required");
                            Updateval.setHint("Please enter Value");
                        }

                    }
                }else {
                    Toast.makeText(ModifyProduct.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
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