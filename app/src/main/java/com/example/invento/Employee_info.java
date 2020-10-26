package com.example.invento;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.invento.auth.Login;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Employee_info extends AppCompatActivity {

    EditText uname,Dob,contaact,experiencee,addresss,positiion;
    //TextView addpic;
    Button Register;
    ImageButton dobbutton;
    CircleImageView userimg;
    private Bitmap bitmap;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    ProgressDialog progressDialog;
    String name,dob,cont,exp,add,pos,userid;
    int year;
    int month;
    int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        dobbutton = findViewById(R.id.dobb);
        userimg = findViewById(R.id.user);
        uname = findViewById(R.id.name);
        Dob = findViewById(R.id.dob);
        contaact = findViewById(R.id.contact);
        experiencee = findViewById(R.id.experience);
        addresss = findViewById(R.id.address);
        positiion = findViewById(R.id.position);
        Register = findViewById(R.id.register);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(Employee_info.this);

        final Global global = (Global)getApplicationContext();
        final String mailid = getIntent().getStringExtra("mailid");

        dobbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Employee_info.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Dob.setText(i2 +"/"+ (i1+1) +"/"+ i);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(Employee_info.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ;

                    Toast.makeText(Employee_info.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(Employee_info.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    choseImag();
                }
            }
        });

        /*if (bitmap != null) {
            Toast.makeText(this, "Invisible", Toast.LENGTH_SHORT).show();
        }*/

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = uname.getText().toString();
                dob = Dob.getText().toString();
                cont = contaact.getText().toString();
                exp = experiencee.getText().toString();
                add = addresss.getText().toString();
                pos = positiion.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(dob) && !TextUtils.isEmpty(cont) && !TextUtils.isEmpty(exp) && !TextUtils.isEmpty(add) && !TextUtils.isEmpty(pos)) {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    if (bitmap == null) {
                        Map<String, Object> userval = new HashMap<>();
                        userval.put("Name", name);
                        userval.put("Userpic", null);
                        userval.put("DOB", dob);
                        userval.put("Contact", cont);
                        userval.put("Address", add);
                        userval.put("Experience", exp);
                        userval.put("Position", pos);
                        userval.put("EmailId", global.getEamilId());



                        firebaseFirestore.collection("Worker log").document(mailid).set(userval).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Map<String, Object> uservalue = new HashMap<>();
                                    uservalue.put("Name", name);
                                    firebaseFirestore.collection("Worker").document(mailid).set(uservalue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(Employee_info.this, "Welcome "+name, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Employee_info.this,MainActivity.class));
                                                finish();
                                            }else {
                                                progressDialog.dismiss();
                                                Toast.makeText(Employee_info.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Employee_info.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    //    startActivity(new Intent(profile.this,Postview.class));
                                    //  finish();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Employee_info.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Employee_info.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);


                        byte[] thumbData = byteArrayOutputStream.toByteArray();


                        firebaseStorage = FirebaseStorage.getInstance();
                        storageReference = firebaseStorage.getReference();
                        storageReference = storageReference.child("user_profile").child(global.getEamilId());

                        UploadTask image_path = storageReference.putBytes(thumbData);
                        Task<Uri> urlTask = image_path.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                // Continue with the task to get the download URL
                                return storageReference.getDownloadUrl();
                            }

                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    final Uri downloadUri = task.getResult();

                                    Map<String, Object> userval = new HashMap<>();
                                    userval.put("Name", name);
                                    userval.put("UserPic", downloadUri.toString());
                                    userval.put("DOB", dob);
                                    userval.put("Contact", cont);
                                    userval.put("Address", add);
                                    userval.put("Experience", exp);
                                    userval.put("Position", pos);
                                    userval.put("EmailId", global.getEamilId());


                                    //uploading hashmap in firebase firestore
                                    firebaseFirestore.collection("Worker log").document(mailid).set(userval).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Map<String, Object> uservalue = new HashMap<>();
                                                uservalue.put("Name", name);
                                                firebaseFirestore.collection("Worker").document(mailid).update(uservalue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            progressDialog.dismiss();
                                                            Toast.makeText(Employee_info.this, "Welcome "+name, Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(Employee_info.this,MainActivity.class));
                                                            finish();
                                                        }else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(Employee_info.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Employee_info.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(Employee_info.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    // Handle failures
                                    // ...
                                    Toast.makeText(Employee_info.this, "failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }

                } else {

                    Toast.makeText(Employee_info.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void choseImag() {
        Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Choose an Image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode== RESULT_OK)
        {
            try {
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputStream);
                userimg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}