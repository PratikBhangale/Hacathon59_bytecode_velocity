package com.example.hacathonbeta.driver;

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
import com.example.hacathonbeta.student.StudentLogin;
import com.example.hacathonbeta.student.StudentSignup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogin extends AppCompatActivity {
    private Button but1;
    private EditText email,password;
    private String email1,password1;
    FirebaseAuth mAuth;
    ProgressBar bar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        mAuth=FirebaseAuth.getInstance();
        but1=(Button) findViewById(R.id.button);
        email=(EditText) findViewById(R.id.editTextTextEmailAddress);
        password=(EditText) findViewById(R.id.editTextTextPassword);


        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_details_and_login();
                bar1.setVisibility(View.VISIBLE);

            }
        });
        progressbarinit();
    }


    private void progressbarinit() {
        bar1=(ProgressBar) findViewById(R.id.bar12);
    }

    private void check_details_and_login() {

        email1=email.getText().toString().trim();
        password1=password.getText().toString().trim();

        if(email1.isEmpty()){
            Toast.makeText(this, "Please enter an Email address", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }
        if(password1.isEmpty()){
            Toast.makeText(this, "Please enter an Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Please enter a valid Email address.");
            email.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    bar1.setVisibility(View.INVISIBLE);
                    gotohome();
                    Toast.makeText(DriverLogin.this, "You have successfully logged in.", Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(DriverLogin.this, "Unable to log you in.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void gotohome() {
        startActivity(new Intent(DriverLogin.this,DriverHome.class));
    }

}