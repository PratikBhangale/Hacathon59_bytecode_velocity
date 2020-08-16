package com.example.hacathonbeta.driver.driver_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hacathonbeta.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserListActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference complaintref = db.collection("Drivers");
    DriverListAdapter driverListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

    setupRecyclerview();


    }


    private void setupRecyclerview() {
        Query query = complaintref.orderBy("date",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Driver> options = new FirestoreRecyclerOptions.Builder<Driver>().setQuery(query,Driver.class).build();
        driverListAdapter = new DriverListAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.driver_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(driverListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        driverListAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        driverListAdapter.stopListening();
    }
}