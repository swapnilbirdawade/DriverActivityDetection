package com.androidtutorialshub.loginregister.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.androidtutorialshub.loginregister.helpers.GPSTracker;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import com.androidtutorialshub.loginregister.R;


public class MainActivity extends ActionBarActivity implements LocationListener, GpsStatus.Listener {

    private SharedPreferences sharedPreferences;
    private LocationManager mLocationManager;
    private static Data data;

    String location=null;
    EditText mobile;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;




    private Toolbar toolbar;

    private TextView satellite;
    private TextView status;
    private TextView accuracy;
    private TextView currentSpeed;
    private Button start,stop,send;
    private TextView maxSpeed;
    private TextView averageSpeed;
    private TextView distance;
    private Chronometer time;
    private Data.onGpsServiceUpdate onGpsServiceUpdate;
    private int count=0;
    private boolean firstfix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        data = new Data(onGpsServiceUpdate);
        start=(Button)findViewById(R.id.start);
        stop=(Button)findViewById(R.id.stop);
        send=(Button)findViewById(R.id.send);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //  toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        //setTitle("");

        onGpsServiceUpdate = new Data.onGpsServiceUpdate() {
            @Override
            public void update() {
                double maxSpeedTemp = data.getMaxSpeed();
                double distanceTemp = data.getDistance();
                double averageTemp;
                if (sharedPreferences.getBoolean("auto_average", false)) {
                    averageTemp = data.getAverageSpeedMotion();
                } else {
                    averageTemp = data.getAverageSpeed();
                }

                String speedUnits;
                String distanceUnits;
                if (sharedPreferences.getBoolean("miles_per_hour", false)) {
                    maxSpeedTemp *= 0.62137119;
                    distanceTemp = distanceTemp / 1000.0 * 0.62137119;
                    averageTemp *= 0.62137119;
                    speedUnits = "mi/h";
                    distanceUnits = "mi";
                } else {
                    speedUnits = "km/h";
                    if (distanceTemp <= 1000.0) {
                        distanceUnits = "m";
                    } else {
                        distanceTemp /= 1000.0;
                        distanceUnits = "km";
                    }
                }

                SpannableString s = new SpannableString(String.format("%.0f", maxSpeedTemp) + speedUnits);
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - 4, s.length(), 0);
                //         maxSpeed.setText(s);

                s = new SpannableString(String.format("%.0f", averageTemp) + speedUnits);
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - 4, s.length(), 0);
                //       averageSpeed.setText(s);

                s = new SpannableString(String.format("%.3f", distanceTemp) + distanceUnits);
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - 2, s.length(), 0);
                //     distance.setText(s);
            }
        };

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //   satellite = (TextView) findViewById(R.id.satellite);
        //   status = (TextView) findViewById(R.id.status);
        //  accuracy = (TextView) findViewById(R.id.accuracy);
        // maxSpeed = (TextView) findViewById(R.id.maxSpeed);
        //averageSpeed = (TextView) findViewById(R.id.averageSpeed);
        ///distance = (TextView) findViewById(R.id.distance);
        //time = (Chronometer) findViewById(R.id.time);
        currentSpeed = (TextView) findViewById(R.id.currentSpeed);
        // progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate) findViewById(R.id.progressBarCircularIndeterminate);

      //  time.setText("00:00:00");
        // time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
        boolean isPair = true;
/*
            @Override
            public void onChronometerTick(Chronometer chrono) {
                long time;
                if (data.isRunning()) {
                    time = SystemClock.elapsedRealtime() - chrono.getBase();
                    data.setTime(time);
                } else {
                    time = data.getTime();
                }

                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                chrono.setText(hh + ":" + mm + ":" + ss);

                if (data.isRunning()) {
                    chrono.setText(hh + ":" + mm + ":" + ss);
                } else {
                    if (isPair) {
                        isPair = false;
                        chrono.setText(hh + ":" + mm + ":" + ss);
                    } else {
                        isPair = true;
                        chrono.setText("");
                    }
                }

            }
        });
    }
*/


        String getlocation="Address :";
        Address address=null;
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            try{

                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
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


            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),"keyStroke count"+count,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,TouchCount.class);
                    startService(intent);
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(MainActivity.this,TouchCount.class);
                    stopService(intent);

                }
            });


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    EditText current_speed=(EditText)findViewById(R.id.speedvalue);
                    String premsg=current_speed.getText().toString();
                    String msg="Mobile Usage while driving";

                    Intent intent=getIntent();
                    String name=intent.getStringExtra("name");
                  //  String ParentMobile=intent.getStringExtra("parent_mobile");



                    //  message.setText(""+location.toString());

                  //  new SmsActivity(0,ParentMobile,location,name).execute();



                    System.out.println(msg+"hii"+premsg+"hii"+location);

                    new DetectionActivity(msg,premsg,location,name).execute();
                    Toast.makeText(getApplicationContext(),"Message sent successfully to ",Toast.LENGTH_LONG).show();



                }
            });


    }



    @Override
    public boolean onTouchEvent(final MotionEvent event)
    {
        System.out.println("Touch Count ="+event.getPointerCount());
       System.out.println("Mask : "+event.getActionMasked());
       count=count+event.getPointerCount();
        if(count>=2)
       Toast.makeText(getApplicationContext(),"KeyStroke Count : "+count,Toast.LENGTH_SHORT).show();
        return true;
    }


    public void onFabClick(View v) {
        if (!data.isRunning()) {
            //   fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause));
            data.setRunning(true);
            time.setBase(SystemClock.elapsedRealtime() - data.getTime());
            time.start();
            data.setFirstTime(true);
            startService(new Intent(getBaseContext(), GpsServices.class));
            // refresh.setVisibility(View.INVISIBLE);
        } else {
            //fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
            data.setRunning(false);
            status.setText("");
            stopService(new Intent(getBaseContext(), GpsServices.class));
            //refresh.setVisibility(View.VISIBLE);
        }
    }

    public void onRefreshClick(View v) {
        resetData();
        stopService(new Intent(getBaseContext(), GpsServices.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstfix = true;
        if (!data.isRunning()) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("data", "");
            data = gson.fromJson(json, Data.class);
        }
        if (data == null) {
            data = new Data(onGpsServiceUpdate);
        } else {
            data.setOnGpsServiceUpdate(onGpsServiceUpdate);
        }

        if (mLocationManager.getAllProviders().indexOf(LocationManager.GPS_PROVIDER) >= 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        } else {
            Log.w("MainActivity", "No GPS location provider found. GPS data display will not be available.");
        }

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      //      showGpsDisabledDialog();
        }

        mLocationManager.addGpsStatusListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
        mLocationManager.removeGpsStatusListener(this);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("data", json);
        prefsEditor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), GpsServices.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, Settings.class);
        //          startActivity(intent);
        //        return true;
        //      }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.hasAccuracy()) {
            SpannableString s = new SpannableString(String.format("%.0f", location.getAccuracy()) + "m");
            s.setSpan(new RelativeSizeSpan(0.75f), s.length() - 1, s.length(), 0);
       //     accuracy.setText(s);

            if (firstfix) {
            //    status.setText("");
                //            fab.setVisibility(View.VISIBLE);
                if (!data.isRunning() || !maxSpeed.getText().equals("")) {
                    //              refresh.setVisibility(View.VISIBLE);
                }
                firstfix = false;
            }
        } else {
            firstfix = true;
        }

        if (location.hasSpeed()) {
            //    progressBarCircularIndeterminate.setVisibility(View.GONE);
            String speed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6) + "km/h";

            if (sharedPreferences.getBoolean("miles_per_hour", false)) { // Convert to MPH
                speed = String.format(Locale.ENGLISH, "%.0f", location.getSpeed() * 3.6 * 0.62137119) + "mi/h";
            }
            SpannableString s = new SpannableString(speed);
            s.setSpan(new RelativeSizeSpan(0.25f), s.length() - 4, s.length(), 0);
            currentSpeed.setText(s);
        }

    }

    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
                int satsInView = 0;
                int satsUsed = 0;
                Iterable<GpsSatellite> sats = gpsStatus.getSatellites();
                for (GpsSatellite sat : sats) {
                    satsInView++;
                    if (sat.usedInFix()) {
                        satsUsed++;
                    }
                }
        //        satellite.setText(String.valueOf(satsUsed) + "/" + String.valueOf(satsInView));
                if (satsUsed == 0) {
                    //fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
                    data.setRunning(false);
//                    status.setText("");
                    stopService(new Intent(getBaseContext(), GpsServices.class));
                    //fab.setVisibility(View.INVISIBLE);
                    //refresh.setVisibility(View.INVISIBLE);
     //               accuracy.setText("");
                   // status.setText(getResources().getString(R.string.waiting_for_fix));
                    firstfix = true;
                }
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    //                showGpsDisabledDialog();
                }
                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                break;
        }
    }


    public void resetData(){
//        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
  //      refresh.setVisibility(View.INVISIBLE);
        time.stop();
        maxSpeed.setText("");
        averageSpeed.setText("");
        distance.setText("");
        time.setText("00:00:00");
        data = new Data(onGpsServiceUpdate);
    }

    public static Data getData() {
        return data;
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}
