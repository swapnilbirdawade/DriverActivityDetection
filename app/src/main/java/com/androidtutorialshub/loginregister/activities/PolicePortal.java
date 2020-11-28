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

public class PolicePortal  extends AsyncTask {
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 1;
    private String username;
    private String message;
    private String date;
    private String location;


    public PolicePortal(String m1,String m2,String m3)
    {
        username=m1;
        message=m2;
        date=m3;
    }

    public PolicePortal(int num)
    {
        byGetOrPost=num;
    }

    protected Object doInBackground(Object[] params) {
        if(byGetOrPost == 0){ //means by Get Method


                return "";

        } else {
            try {

                String link = IPsetting.ip+"police.php";

                String data = URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" +
                        URLEncoder.encode(message, "UTF-8");
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

            return toString();


    }
    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}
