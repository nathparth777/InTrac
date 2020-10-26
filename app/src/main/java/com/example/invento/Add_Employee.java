package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.invento.auth.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_Employee extends AppCompatActivity {

    EditText Email, Pass, Repass;
    Button Create;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__employee);

        Email = findViewById(R.id.email);
        Pass = findViewById(R.id.pass);
        Repass = findViewById(R.id.repass);
        Create = findViewById(R.id.create);
        progressDialog = new ProgressDialog(Add_Employee.this);

        mAuth = FirebaseAuth.getInstance();

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                final String mEmail = Email.getText().toString();
                String mPass = Pass.getText().toString();
                String mRepass = Repass.getText().toString();

                if (!TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPass) && !TextUtils.isEmpty(mRepass)){
                    if (mPass.equals(mRepass)){
                        mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    firestore = FirebaseFirestore.getInstance();

                                    Map<String, Object> addemployee = new HashMap<>();
                                    addemployee.put("LogVal", "0");
                                    addemployee.put("EmailId", mEmail.toLowerCase());
                                    firestore.collection("Worker").document(mEmail.toLowerCase()).set(addemployee).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                Toast.makeText(Add_Employee.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Add_Employee.this,Admin_Home.class));
                                                finish();
                                            }else{
                                                progressDialog.dismiss();
                                                Toast.makeText(Add_Employee.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Add_Employee.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(Add_Employee.this,"Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        progressDialog.dismiss();
                        Repass.setError("Repassword is not Match");
                    }
                }else{
                    progressDialog.dismiss();
                    if (TextUtils.isEmpty(mEmail)){
                        Email.setError("Email-ID is required!");
                        Email.setHint("Please enter Email-ID");
                    }
                    if (TextUtils.isEmpty(mPass)) {
                        Pass.setError("Password is required!");
                        Pass.setHint("Please enter Password");
                    }
                    if (TextUtils.isEmpty(mRepass)) {
                        Repass.setError("Password is required!");
                        Repass.setHint("Please enter Repassword");
                    }
                }
            }
        });
    }
}