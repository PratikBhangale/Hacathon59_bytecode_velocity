package com.example.hacathonbeta.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hacathonbeta.Chat.ChatActivity;
import com.example.hacathonbeta.R;
import com.example.hacathonbeta.driver.driver_list.UserListActivity;

public class StudentHome extends AppCompatActivity {

    private Button but12,button,buttonchat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        buttonchat = (Button) findViewById(R.id.button10);
        buttonchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHome.this, ChatActivity.class));
            }
        });
        but12 = (Button) findViewById(R.id.button6);
        but12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHome.this,StudentProfile.class));
            }
        });

        button = (Button) findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentHome.this, UserListActivity.class));
            }
        });
    }
}