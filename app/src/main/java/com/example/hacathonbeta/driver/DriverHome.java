package com.example.hacathonbeta.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.MainActivity;
import com.example.hacathonbeta.R;
import com.example.hacathonbeta.Services.LocationService;
import com.example.hacathonbeta.driver.driver_list.Driver;
import com.example.hacathonbeta.driver.driver_list.DriverLocationObject;
import com.example.hacathonbeta.driver.driver_list.UserListActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
    private static final String TAG = "DriverHometag";
    private FirebaseFirestore firebaseFirestore;
    private Button but, signout;
    private FirebaseAuth mAuth;
    private DriverLocationObject driverLocationObject;
    private FusedLocationProviderClient mfusedLocationProviderClient;
    private GeoPoint geoPoint1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        signout = (Button) findViewById(R.id.button8) ;
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(DriverHome.this,DriverLogin.class));
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        but = (Button) findViewById(R.id.button4);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverHome.this, DriverProfile.class));
            }
        });



        //Getting the location of the bus driver

        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastKnownLocation();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(DriverHome.this, LocationService.class);
            //this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                DriverHome.this.startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.example.hacathonbeta.Services.Locationservice".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        return false;
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mfusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener< Location >() {
            @Override
            public void onComplete(@NonNull Task< Location > task) {
                Location location = task.getResult();
                assert location != null;
                geoPoint1 = new GeoPoint(location.getLatitude(), location.getLongitude());
                saveLocationToFirestore(geoPoint1);
                startLocationService();
            }
        });
    }

    private void saveLocationToFirestore(GeoPoint point) {
        Map<String, Object> Driver1 = new HashMap<>();
        Driver1.put("Geo_point",point);
        firebaseFirestore.collection("Driver Location").document(Objects.requireNonNull(mAuth.getUid())).set(Driver1).addOnCompleteListener(new OnCompleteListener< Void >() {
            @Override
            public void onComplete(@NonNull Task< Void > task) {
                if(task.isSuccessful()){
                    Toast.makeText(DriverHome.this, "Location Uploaded", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DriverHome.this, "Location Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}