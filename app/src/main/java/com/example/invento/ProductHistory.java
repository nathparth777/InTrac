package com.example.invento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProductHistory extends AppCompatActivity {

    TextView Add, Delete, delby;
    private RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_history);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerview);

        Add = findViewById(R.id.add);
        Delete = findViewById(R.id.delete);
        delby =findViewById(R.id.delby);

        Add.setText(getIntent().getStringExtra("add"));
        if(getIntent().getStringExtra("delete") != null){
            delby.setVisibility(View.VISIBLE);
            Delete.setVisibility(View.VISIBLE);
            Delete.setText(getIntent().getStringExtra("delete"));
        }else{
            delby.setVisibility(View.GONE);
            Delete.setVisibility(View.GONE);
        }
        String barid = getIntent().getStringExtra("barid");

        Query query = firebaseFirestore.collection("Product log").document(barid).collection(barid).orderBy("Date_of_Modify", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ModelProLog> options = new FirestoreRecyclerOptions.Builder<ModelProLog>()
                .setQuery(query,ModelProLog.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ModelProLog, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productlog_list,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelProLog model) {
                holder.Date.setText(model.getDate_of_Modify());
                holder.Modify.setText(model.getModify_by());
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Date, Modify;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.mdate);
            Modify = itemView.findViewById(R.id.modify);
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