package com.example.hacathonbeta.driver.driver_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
    private TextView bus,id;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setupRecyclerview();

        bus = (TextView) findViewById(R.id.textView17);
        id = (TextView) findViewById(R.id.textView18);


        driverListAdapter.setOnItemClickListener(new DriverListAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(DocumentSnapshot documentSnapshot, int position) {
                Driver driver = documentSnapshot.toObject(Driver.class);
                String bus_id1 = documentSnapshot.getString("bus");
                String id1 = documentSnapshot.getId();
                Intent intent = new Intent(UserListActivity.this,AllMapsActivity.class);
                intent.putExtra("bus_number",bus_id1);
                intent.putExtra("driver_UID",id1);
                startActivity(intent);

                bus.setText(bus_id1);
                id.setText(id1);
            }
        });
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