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

public class AddDelete_Details extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete__details);

        String id = getIntent().getStringExtra("EmailId");

        recyclerView = findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Worker log").document(id).collection("AddDelete").orderBy("Date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<adddelete_model> options = new FirestoreRecyclerOptions.Builder<adddelete_model>()
                .setQuery(query,adddelete_model.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<adddelete_model, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adddelete_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull adddelete_model model) {
                holder.Date.setText(model.getDate());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String date = getItem(position).getDate();
                        String add = getItem(position).getItem_Added();
                        String del = getItem(position).getItem_Deleted();

                        Intent intent = new Intent(AddDelete_Details.this,AddDelete_Details2.class);
                        intent.putExtra("Date",date);
                        intent.putExtra("Add",add);
                        intent.putExtra("Del",del);
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

        private TextView Date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Date =itemView.findViewById(R.id.date);

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