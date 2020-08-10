package com.example.hacathonbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hacathonbeta.driver.DriverLogin;
import com.example.hacathonbeta.student.StudentLogin;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button but1,but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but1=(Button) findViewById(R.id.button1);
        but2=(Button) findViewById(R.id.button2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDriverlogin();
            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoStudentlogin();
            }
        });
    }

    private void gotoStudentlogin() {
        startActivity(new Intent(MainActivity.this, StudentLogin.class));

    }

    private void gotoDriverlogin() {
        startActivity(new Intent(MainActivity.this, DriverLogin.class));
    }
}