package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Employees extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        recyclerView = findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Worker log").orderBy("EmailId").startAt("a").endAt("z");

        FirestoreRecyclerOptions<employee_model> options = new FirestoreRecyclerOptions.Builder<employee_model>()
                .setQuery(query,employee_model.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<employee_model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull employee_model model) {
                holder.Id.setText(model.getEmailId());
                holder.Name.setText(model.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = getItem(position).getName();
                        String id = getItem(position).getEmailId();
                        String dob = getItem(position).getDOB();

                        String contact = getItem(position).getContact();
                        String pos = getItem(position).getPosition();
                        String experience = getItem(position).getExperience();
                        String address = getItem(position).getAddress();

                        Intent intent = new Intent(Employees.this,EmployeesDetails.class);
                        intent.putExtra("Name",name);
                        intent.putExtra("DOB",dob);

                        intent.putExtra("ID",id);
                        intent.putExtra("Contact",contact);
                        intent.putExtra("Pos",pos);
                        intent.putExtra("Experience",experience);
                        intent.putExtra("Address",address);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Id, Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.eemail);
            Name = itemView.findViewById(R.id.ename);

        }
    }

    private void firebaseSearch(String searchText){

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        String query = searchText.toLowerCase();
        //Toast.makeText(this, ""+query, Toast.LENGTH_SHORT).show();
        Query firebaseSearchQuery = firebaseFirestore.collection("Worker log").orderBy("EmailId").startAt(query).endAt(query + "\uf8ff");
        FirestoreRecyclerOptions<employee_model> options = new FirestoreRecyclerOptions.Builder<employee_model>().setQuery(firebaseSearchQuery,employee_model.class).build();

        adapter = new FirestoreRecyclerAdapter<employee_model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull employee_model model) {
                holder.Id.setText(model.getEmailId());
                holder.Name.setText(model.getName());
            }
        };

        adapter.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                firebaseSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                firebaseSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}