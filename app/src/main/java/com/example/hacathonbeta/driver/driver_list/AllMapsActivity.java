package com.example.hacathonbeta.driver.driver_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hacathonbeta.R;

public class AllMapsActivity extends AppCompatActivity {
    private static final String TAG = "set_user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_maps);
        if(getIntent().hasExtra("bus_number") && getIntent().hasExtra("driver_UID")){
            String one = getIntent().getStringExtra("bus_number");
            String two = getIntent().getStringExtra("driver_UID"); }

    }


}