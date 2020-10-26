package com.example.invento.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.invento.Add_ProfileDetail;
import com.example.invento.Admin_Home;
import com.example.invento.Employee_info;
import com.example.invento.Global;
import com.example.invento.MainActivity;
import com.example.invento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText Email, Password;
    Button Login;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(Login.this);

        mAuth = FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );

                final String mEmail = Email.getText().toString();
                String mPassword = Password.getText().toString();

                if (!TextUtils.isEmpty(mEmail) && !TextUtils.isEmpty(mPassword)){
                    mAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Global global = (Global)getApplicationContext();
                                global.setEamilId(mEmail.toLowerCase());

                                //For Admin page
                                firestore = FirebaseFirestore.getInstance();

                                firestore.collection("Worker").document(global.getEamilId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            int value = Integer.parseInt(task.getResult().getString("LogVal"));
                                            String name = task.getResult().getString("Name");
                                            progressDialog.dismiss();
                                            if (value==1){

                                                startActivity(new Intent(Login.this, Admin_Home.class));
                                                finish();
                                            }else if(name==null){
                                                Intent intent = new Intent(Login.this,Employee_info.class);
                                                intent.putExtra("mailid",mEmail);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                startActivity(new Intent(Login.this,MainActivity.class));
                                            }

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Error: "+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    if (TextUtils.isEmpty(mEmail)){
                        Email.setError("Email-ID is required!");
                        Email.setHint("Please enter Email-ID");
                    }
                    if (TextUtils.isEmpty(mPassword)) {
                        Password.setError("Password is required!");
                        Password.setHint("Please enter Password");
                    }
                }
            }
        });

    }
}