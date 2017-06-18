package com.example.alias.prototype2;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    protected Location mLastLocation;
    public static final String EXTRA_MESSAGE = "prototo1.MESSAGE";
    public static final String EXTRAEXTRA_MESSAGE = "prototo1.MESSAGEEXTRA";
    public String mLatitudeLabel;
    public String mLongitudeLabel;
    public TextView mLatitudeText;
    public TextView mLongitudeText;
    public double message1;
    public double message2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                             mLastLocation = task.getResult();

                            mLatitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLatitudeLabel,
                                    mLastLocation.getLatitude()));

                            mLongitudeText.setText(String.format(Locale.ENGLISH, "%s: %f",
                                    mLongitudeLabel,
                                    mLastLocation.getLongitude()));
                            Toast.makeText(getApplicationContext(),"congrats it works..now make it better",Toast.LENGTH_SHORT).show();
                             message1=mLastLocation.getLatitude();
                             message2= mLastLocation.getLongitude();

                        }

                        else {
                            /***Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));***/
                            Toast.makeText(getApplicationContext(),"congrats it doesn't work..now make it",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(EXTRA_MESSAGE,message1);
        intent.putExtra(EXTRAEXTRA_MESSAGE,message2);
        startActivity(intent);

    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            startLocationPermissionRequest();
        }

        }
    public void newscreen(View view){
        Intent intent = new Intent(this, DisplayPhoto.class);
        startActivity(intent);
    }
    public void mapscreen(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
