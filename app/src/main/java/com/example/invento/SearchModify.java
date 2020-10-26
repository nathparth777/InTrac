package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SearchModify extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_modify);

        recyclerView = findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();

        final String id = getIntent().getStringExtra("EmailId");

        Query query = firebaseFirestore.collection("Worker log").document(id).collection("SearchModify").orderBy("id", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<searchmodify_model> options = new FirestoreRecyclerOptions.Builder<searchmodify_model>()
                .setQuery(query,searchmodify_model.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<searchmodify_model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchmodify_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull searchmodify_model model) {
                holder.Id.setText(model.getId());


               holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ID = getItem(position).getId();

                        Intent intent = new Intent(SearchModify.this,searchmodify_details.class);
                        intent.putExtra("id",ID);
                        intent.putExtra("emailid",id);
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

        private TextView Id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Id =itemView.findViewById(R.id.id);

        }
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
}