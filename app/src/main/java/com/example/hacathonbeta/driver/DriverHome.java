package com.example.hacathonbeta.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hacathonbeta.R;
import com.example.hacathonbeta.driver.driver_list.UserListActivity;

public class DriverHome extends AppCompatActivity {

    private Button but,userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        but = (Button) findViewById(R.id.button4);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverHome.this,DriverProfile.class));
            }
        });

        userlist= (Button) findViewById(R.id.button7);
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverHome.this, UserListActivity.class));
            }
        });
    }
}