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

public class searchmodify_details extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchmodify_details);

        String id = getIntent().getStringExtra("id");
        String emailid = getIntent().getStringExtra("emailid");

        recyclerView = findViewById(R.id.recyclerview);
        firebaseFirestore = FirebaseFirestore.getInstance();

        Query query = firebaseFirestore.collection("Worker log").document(emailid).collection("SearchModify").document(id).collection("Activity").orderBy("Date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<searchmodify_model2> options = new FirestoreRecyclerOptions.Builder<searchmodify_model2>()
                .setQuery(query,searchmodify_model2.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<searchmodify_model2, ViewHolder>(options){

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchmodify_details_list2,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull searchmodify_model2 model) {
                holder.Date.setText(model.getDate());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String activity = getItem(position).getActivity();
                        String Amrp = getItem(position).getAfter_modify_MRP();
                        String Aname = getItem(position).getAfter_modify_Name();
                        String Aquantity = getItem(position).getAfter_modify_Quantity();
                        String Bmrp = getItem(position).getBefore_modify_MRP();
                        String Bname = getItem(position).getBefore_modify_Name();
                        String Bquantity = getItem(position).getBefore_modify_Quantity();
                        String date=getItem(position).getDate();

                        Intent intent = new Intent(searchmodify_details.this,searchmodify_details2.class);
                        intent.putExtra("Activity",activity);
                        intent.putExtra("Date",date);
                        intent.putExtra("amrp",Amrp);
                        intent.putExtra("aname",Aname);
                        intent.putExtra("aquantity",Aquantity);
                        intent.putExtra("bmrp",Bmrp);
                        intent.putExtra("bname",Bname);
                        intent.putExtra("bquantity",Bquantity);



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