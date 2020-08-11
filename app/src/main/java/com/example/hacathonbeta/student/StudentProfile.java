package com.example.hacathonbeta.student;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class StudentProfile extends AppCompatActivity {

    private TextView name,route,stop,edit;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        mRef = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        name = (TextView) findViewById(R.id.textView12);
        route = (TextView) findViewById(R.id.textView13);
        stop = (TextView) findViewById(R.id.textView14);
        edit = (TextView) findViewById(R.id.textView15);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this,EditStudentProfile.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshdata();
    }

    private void refreshdata() {
        Task<DocumentSnapshot> mDatasnapshot = mRef.collection("Students").document(mAuth.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String sname = documentSnapshot.getString("name");
                            String sroute = documentSnapshot.getString("bus_route");
                            String sstop = documentSnapshot.getString("bus_stop");

                            name.setText(sname);
                            route.setText(sroute);
                            stop.setText(sstop);

                            Toast.makeText(StudentProfile.this, "Refresh Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(StudentProfile.this, "This document does not exist.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentProfile.this, "Refresh Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}