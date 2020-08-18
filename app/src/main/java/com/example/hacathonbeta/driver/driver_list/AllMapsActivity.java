package com.example.hacathonbeta.driver.driver_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

public class AllMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Task<DocumentSnapshot>  noteRef ;
    private TextView location;
    private Boolean mLocationPermissionsGranted = false;
    private FirebaseFirestore mRef;
    private FirebaseAuth mAuth;
    private GeoPoint geoPoint;
    private String two,one;
    private LatLng latLng;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_maps);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseFirestore.getInstance();
        if(getIntent().hasExtra("bus_number") && getIntent().hasExtra("driver_UID")){

            one = getIntent().getStringExtra("bus_number");

            two = getIntent().getStringExtra("driver_UID");
        }
        getLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(AllMapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");

        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            if(two.equals("B2gtqyUZVnhJaS0h0tD73hcADuq2")){
                getDeviceLocationone();
            }

            if(two.equals("ntOh68QKyIRcm96oXuvckTMVqZU2")){
                getDeviceLocationtwo();
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    private void getDeviceLocationone() {
        noteRef = mRef.collection("Driver Location").document("B2gtqyUZVnhJaS0h0tD73hcADuq2").get()
                .addOnSuccessListener(new OnSuccessListener< DocumentSnapshot >() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            geoPoint = documentSnapshot.getGeoPoint("geoPoint");
                            assert geoPoint != null;
                            double lat = geoPoint.getLatitude();
                            double lng = geoPoint.getLongitude ();
                            latLng = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("your_title"));
                            moveCamera(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),DEFAULT_ZOOM);

                        }
                    }
                });
    }
    private void getDeviceLocationtwo() {
        noteRef = mRef.collection("Driver Location").document("ntOh68QKyIRcm96oXuvckTMVqZU2").get()
                .addOnSuccessListener(new OnSuccessListener< DocumentSnapshot >() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            geoPoint = documentSnapshot.getGeoPoint("geoPoint");
                            assert geoPoint != null;
                            double lat = geoPoint.getLatitude();
                            double lng = geoPoint.getLongitude ();
                            latLng = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("Bus Two"));
                            moveCamera(new LatLng(geoPoint.getLatitude(),geoPoint.getLongitude()),DEFAULT_ZOOM);
                        }
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionsGranted = false;
                        Log.d(TAG, "onRequestPermissionsResult: permission failed");
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                mLocationPermissionsGranted = true;
                //initialize our map
                initMap();
            }
        }
    }
}