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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Message extends AppCompatActivity {

    EditText Message;
    Button Send;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Message = findViewById(R.id.notic);
        Send = findViewById(R.id.send);
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(Message.this);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = Message.getText().toString();

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                final String date = simpleDateFormat.format(cal.getTime());

                if (!TextUtils.isEmpty(message)){

                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );

                    Map<String, Object> msg = new HashMap<>();
                    msg.put("Message", message);
                    msg.put("Date", date);

                    firebaseFirestore.collection("Admin Message").document("message").set(msg).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(Message.this, "Message Send", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Message.this,Admin_Home.class));
                                finish();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(Message.this, "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Message.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(Message.this, "Please enter message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}