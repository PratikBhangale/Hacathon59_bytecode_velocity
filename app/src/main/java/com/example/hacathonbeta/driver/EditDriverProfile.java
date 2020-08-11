package com.example.hacathonbeta.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditDriverProfile extends AppCompatActivity {
    private EditText name, number;
    FirebaseFirestore db;
    FirebaseAuth kAuth;
    private String names, numbers;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver_profile);
        db = FirebaseFirestore.getInstance();
        kAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.editname);
        number = findViewById(R.id.editphone);
        save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                names = name.getText().toString().trim();
                numbers = number.getText().toString().trim();
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("name", names);
                user.put("bus", numbers);

                // Add a new document with a generated ID
                db.collection("Drivers").document(Objects.requireNonNull(kAuth.getUid())).set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditDriverProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditDriverProfile.this, "Profile not updated", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}