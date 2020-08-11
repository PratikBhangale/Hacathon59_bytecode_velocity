package com.example.hacathonbeta.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hacathonbeta.R;

public class StudentHome extends AppCompatActivity {

    private Button but12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        but12 = (Button) findViewById(R.id.button6);
        but12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHome.this,StudentProfile.class));
            }
        });
    }
}