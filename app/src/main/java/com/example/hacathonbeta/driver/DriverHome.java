package com.example.hacathonbeta.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.example.hacathonbeta.driver.driver_list.Driver;
import com.example.hacathonbeta.driver.driver_list.UserListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DriverHome extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude, longitude;
    FirebaseFirestore firebaseFirestore;
    private Button but,userlist;
    FirebaseAuth mAuth;
    Driver driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocation = findViewById(R.id.showLocation);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        //Getting the location of the bus driver
        if(mAuth.getCurrentUser() != null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                OnGPS();
            } else {
                getLocation();
            }
        }

    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @SuppressLint("SetTextI18n")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                DriverHome.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                DriverHome.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                final GeoPoint geoPoint = new GeoPoint(locationGPS.getLatitude(),locationGPS.getLongitude());
                DocumentReference documentReference = firebaseFirestore.collection("Drivers").document(Objects.requireNonNull(mAuth.getUid()));
                Map<String,Object> driver =new HashMap<>();
                driver.put("geoPoint",geoPoint);
                firebaseFirestore.collection("Driver Location").document(mAuth.getUid()).set(driver).addOnCompleteListener(new OnCompleteListener< Void >() {
                    @Override
                    public void onComplete(@NonNull Task< Void > task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DriverHome.this, "Update successful.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}