package com.androidtutorialshub.loginregister.activities;


import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.helpers.GPSTracker;

import java.util.List;

import java.util.Locale;

import android.util.Log;
import android.widget.Toast;


public class DriverSafetyActivity extends AppCompatActivity implements LocationListener {

    Button panic;
    EditText message;
    String lat, lot;
    String location=null;
    EditText mobile;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencyhelp);
        getSupportActionBar().setTitle("");

        panic = (Button) findViewById(R.id.panic);
        message = (EditText) findViewById(R.id.message);




        String getlocation="Address :";
        Address address=null;
        gps = new GPSTracker(DriverSafetyActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            try{

                Geocoder geocoder = new Geocoder(DriverSafetyActivity.this, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(
                        latitude, longitude, 1);
                if (addressList != null && addressList.size() > 0) {
                    address = addressList.get(0);
                    address.getLocality();
                }
                getlocation +=address.toString();

            }catch(Exception e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG);
            }
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude+" "+getlocation, Toast.LENGTH_LONG).show();

            // location="Latitude : "+latitude+"And Longitude : "+longitude;
            location="https://maps.google.com/maps?q="+latitude+","+longitude+"";
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /* Intent email=new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"shekhar13gosavi@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,"Emergency");
                String msg=message.getText().toString();
                email.putExtra(Intent.EXTRA_TEXT,msg);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Choose an Email client"));
                */
                String msg=message.getText().toString();
                String premsg="Need Emergency Help!!!!!";

                Intent intent=getIntent();
                String name=intent.getStringExtra("name");
                String ParentMobile=intent.getStringExtra("parent_mobile");



              //  message.setText(""+location.toString());

                new SmsActivity(0,ParentMobile,location,name).execute();
                new DriverActivity(msg,premsg,location,name).execute();
                Toast.makeText(getApplicationContext(),"Message sent successfully to "+ParentMobile,Toast.LENGTH_LONG).show();




                // Instantiate the RequestQueue.



            }

        });





    }
    @Override
    public void onLocationChanged(Location location) {
       // txtLat = (TextView) findViewById(R.id.textview1);
        //txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        double lat=location.getLatitude();
        double lot=location.getLongitude();
        //System.out.println("Shkehar"+lat+lot);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }





}
