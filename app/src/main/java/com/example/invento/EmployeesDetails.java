package com.example.invento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmployeesDetails extends AppCompatActivity {

    TextView Name, EmailId, DOB, Contact, Position, Experience, Address;
    Button AddDelete, SearchModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_details);

        Name = findViewById(R.id.Ename);
        EmailId = findViewById(R.id.Eemail);
        DOB = findViewById(R.id.Edob);

        Contact = findViewById(R.id.Econtact);
        Position = findViewById(R.id.Epos);
        Experience = findViewById(R.id.Eexperience);
        Address = findViewById(R.id.Eaddress);
        AddDelete = findViewById(R.id.adddelete);
        SearchModify = findViewById(R.id.searchmodify);

        Name.setText(getIntent().getStringExtra("Name"));
        EmailId.setText(getIntent().getStringExtra("ID"));
        DOB.setText(getIntent().getStringExtra("DOB"));

        Contact.setText(getIntent().getStringExtra("Contact"));
        Position.setText(getIntent().getStringExtra("Pos"));
        Experience.setText(getIntent().getStringExtra("Experience"));
        Address.setText(getIntent().getStringExtra("Address"));

        AddDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeesDetails.this,AddDelete_Details.class);
                intent.putExtra("EmailId",EmailId.getText().toString());
                startActivity(intent);

            }
        });

        SearchModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(EmployeesDetails.this,SearchModify.class);
                intent1.putExtra("EmailId",EmailId.getText().toString());
                startActivity(intent1);
            }
        });
    }
}