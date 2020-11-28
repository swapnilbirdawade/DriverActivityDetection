package com.androidtutorialshub.loginregister.activities;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class HospitalPortal  extends AsyncTask {
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 1;
    private String username;
    private String hospital_name;
    private String date;


    public HospitalPortal(String m1,String m2,String loc)
    {
        username=m1;
        hospital_name=m2;
        date=loc;
    }

    public HospitalPortal(int num)

    {
        byGetOrPost=num;
    }

    protected Object doInBackground(Object[] params) {
        if(byGetOrPost == 0){ //means by Get Method


            return "";

        } else {
            try {

                String link = IPsetting.ip+"hospital.php";

                String data = URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("hospital_name", "UTF-8") + "=" +
                        URLEncoder.encode(hospital_name, "UTF-8");
                data += "&" + URLEncoder.encode("date", "UTF-8") + "=" +
                        URLEncoder.encode(date, "UTF-8");


                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;

                }

                return sb.toString();


            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

    }


    public String access ()
    {
        try {
            String PhoneNumber = "9975321007";
            String message = URLEncoder.encode("Please Help.. its emergency", "ISO-8859-1");
            // String PhoneNumber="";
            String user = "vinodotp";
            String password = "123123";
            String sender = "CAPTCH";
            String link = "http://www.smswave.in/panel/sendsms.php" + "?PhoneNumber=" + PhoneNumber + "&Text=" + message + "&user=vinodotp&password=123123&sender=CAPTCH";
            ;

            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }

            in.close();
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }

    }
    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}
