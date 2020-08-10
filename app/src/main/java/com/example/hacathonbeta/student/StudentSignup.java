package com.example.hacathonbeta.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentSignup extends AppCompatActivity {
    private Button but1;
    private EditText email,password;
    private TextView tosignup;
    private String email2,password2;
    FirebaseAuth mAuth;
    ProgressBar bar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        mAuth=FirebaseAuth.getInstance();

        but1 = (Button) findViewById(R.id.button3);
        email = (EditText) findViewById(R.id.editTextEmailAddress2);
        password = (EditText) findViewById(R.id.editTextTextPassword2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_and_signup();
                bar1.setVisibility(View.VISIBLE);
            }
        });
        progressbarinit();
    }


    private void progressbarinit() {
        bar1=(ProgressBar) findViewById(R.id.bar);
    }

    private void check_and_signup() {
        email2 = email.getText().toString().trim();
        password2 = password.getText().toString().trim();

        if (email2.isEmpty()) {
            Toast.makeText(this, "Please enter an Email address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }
        if (password2.isEmpty()) {
            Toast.makeText(this, "Please enter an Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            email.setError("Please enter a valid Email address.");
            email.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    gotohome();
                    Toast.makeText(StudentSignup.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(StudentSignup.this, "Signup Uncessful", Toast.LENGTH_SHORT).show();
                }
                bar1.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void gotohome() {
        startActivity(new Intent(StudentSignup.this,StudentHome.class));
    }
}