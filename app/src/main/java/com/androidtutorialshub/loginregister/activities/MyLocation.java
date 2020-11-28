package com.androidtutorialshub.loginregister.activities;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class MyLocation implements android.location.LocationListener {

    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        Log.e("MY CURRENT LOCATION", myLocation);
        System.out.println("hii"+myLocation);
       // Toast.makeText(DriverSafetyActivity.this,myLocation,Toast.LENGTH_LONG);

    }

    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }
}