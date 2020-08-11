package com.example.hacathonbeta.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DriverProfile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Task<DocumentSnapshot> mRef;
    private TextView name;
    private TextView bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        name = (TextView) findViewById(R.id.textView7);
        bus = (TextView) findViewById(R.id.textView8);
        TextView edit = (TextView) findViewById(R.id.textView9);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverProfile.this,EditDriverProfile.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getprofile();
    }

    private void getprofile() {
        mRef = db.collection("Drivers").document(Objects.requireNonNull(mAuth.getUid())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String drivername = documentSnapshot.getString("name");
                String driverbus = documentSnapshot.getString("bus");
                name.setText(drivername);
                bus.setText(driverbus);
                Toast.makeText(DriverProfile.this, "Refresh Successful", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DriverProfile.this, "Refresh Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

