package com.example.hacathonbeta.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditStudentProfile extends AppCompatActivity {
// commit troubleshoot branch
    private EditText name,route,addr;
    private Button but1;
    FirebaseFirestore mRef = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        name = (EditText) findViewById(R.id.editTextPersonName);
        route = (EditText) findViewById(R.id.editTextbusroute);
        addr = (EditText) findViewById(R.id.editTextbusstop);
        but1 = (Button) findViewById(R.id.button5);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveprofile();
            }
        });
    }

    private void saveprofile() {

        String sname = name.getText().toString().trim();
        String sroute = route.getText().toString().trim();
        String saddr = addr.getText().toString().trim();

        Map<String,Object> student = new HashMap<>();
        student.put("name",sname);
        student.put("bus_route",sroute);
        student.put("bus_stop",saddr);

        mRef.collection("Students").document(mAuth.getUid()).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditStudentProfile.this, "Profile saved.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditStudentProfile.this, "Unable to save profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}