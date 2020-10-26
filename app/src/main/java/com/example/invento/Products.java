package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Products extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview);



        Query query = firebaseFirestore.collection("Product by name").orderBy("NameSL").startAt("a").endAt("z");

        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query,Model.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pro_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull Model model) {
                holder.Id.setText(model.getID());
                holder.Name.setText(model.getSubname());
                holder.Progress.setProgress(Integer.parseInt(model.getTotal_Items()));
                holder.Value.setText(model.getTotal_Items());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = getItem(position).getName();
                        String weight = getItem(position).getWeight();
                        String mrp = getItem(position).getMRP();
                        String id = getItem(position).getID();
                        String quantity = getItem(position).getTotal_Items();
                        String date = getItem(position).getDate_of_Adding();
                        String manufacture = getItem(position).getManufacture_Date();
                        String expiry = getItem(position).getExpiry_Date();
                        Intent intent = new Intent(Products.this,Products_Details.class);
                        intent.putExtra("Name",name);
                        intent.putExtra("Weight",weight);
                        intent.putExtra("MRP",mrp);
                        intent.putExtra("ID",id);
                        intent.putExtra("Quantity",quantity);
                        intent.putExtra("Date",date);
                        intent.putExtra("Manufacture",manufacture);
                        intent.putExtra("Expiry",expiry);
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

        private TextView Id, Name, Value;
        private ProgressBar Progress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Id = itemView.findViewById(R.id.id);
            Name = itemView.findViewById(R.id.name);
            Progress = itemView.findViewById(R.id.progress);
            Value = itemView.findViewById(R.id.value);
        }
    }
    //Search
    private void firebaseSearch(String searchText){

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        String query = searchText.toLowerCase();
        //Toast.makeText(this, ""+query, Toast.LENGTH_SHORT).show();
        Query firebaseSearchQuery = firebaseFirestore.collection("Product by name").orderBy("NameSL").startAt(query).endAt(query + "\uf8ff");
        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery,Model.class).build();

        adapter = new FirestoreRecyclerAdapter<Model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pro_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                holder.Id.setText(model.getID());
                holder.Name.setText(model.getSubname());
                holder.Progress.setProgress(Integer.parseInt(model.getTotal_Items()));
                holder.Value.setText(model.getTotal_Items());
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