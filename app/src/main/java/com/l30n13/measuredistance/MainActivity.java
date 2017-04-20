package com.l30n13.measuredistance;

import android.databinding.DataBindingUtil;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.l30n13.measuredistance.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private TrackGPS gps;
    private double longitude;
    private double latitude;

    private Double destinationLat = 23.733023;//Dhaka University Lat
    private Double destinationLong = 90.398384;//Dhaka University Long
    private int distanceRadius = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AccessPermission.accessPermission(this);
        gps = new TrackGPS(MainActivity.this);
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
            Toast.makeText(getApplicationContext(), "Longitude:" + String.valueOf(longitude) + "\nLatitude:" + String.valueOf(latitude), Toast.LENGTH_SHORT).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void getLatLong() {
        AccessPermission.accessPermission(this);
        gps = new TrackGPS(MainActivity.this);
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }
    }

    public void checkDistance(View view) {
        getLatLong();
        try {
            if (activityMainBinding.etLat.toString().equals("") && activityMainBinding.etLong.toString().equals("")) {
                destinationLat = 23.733023;//Dhaka University Lat
                destinationLong = 90.398384;//Dhaka University Long
            } else {
                destinationLat = Double.valueOf(activityMainBinding.etLat.getText().toString());
                destinationLong = Double.valueOf(activityMainBinding.etLong.getText().toString());
            }
            if (!activityMainBinding.etDistance.getText().toString().equals("")) {
                distanceRadius = Integer.valueOf(activityMainBinding.etDistance.getText().toString());
            } else {
                distanceRadius = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        float[] results = new float[3];
        Location.distanceBetween(destinationLat, destinationLong, latitude, longitude, results);

        if (distanceRadius == 0) {
            Toast.makeText(getApplicationContext(), "You are " + Math.round(results[0] / 1000) + " kilometer far from your expected location", Toast.LENGTH_SHORT).show();
        }
        if (Math.round(results[0] / 1000) <= distanceRadius) {
            Toast.makeText(getApplicationContext(), "Your expected location is near you.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "You are " + (Math.round(results[0] / 1000) - distanceRadius) + " kilometer far from your expected location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
}
