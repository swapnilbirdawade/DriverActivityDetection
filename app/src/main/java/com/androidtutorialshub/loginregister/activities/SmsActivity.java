package com.androidtutorialshub.loginregister.activities;


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
import android.widget.Toast;

public class SmsActivity  extends AsyncTask{
    private TextView statusField,roleField;
    private Context context;
    private int byGetOrPost = 0;
    private String mobile;
    private String msg;
    private String premsg;
    private String location;
    private String user_name;

    public SmsActivity(int num,String mobileno,String loc,String name)
    {
        mobile=mobileno;
        byGetOrPost=num;
        location=loc;
        user_name=name;
    }

    protected Object doInBackground(Object[] params) {
        if(byGetOrPost == 0){ //means by Get Method
            try {
                System.out.println(mobile);
                String PhoneNumber = "9096602619";
                location +="  [From : "+user_name+"]";
                String message = URLEncoder.encode(location, "ISO-8859-1");

                // String PhoneNumber="";
                String user = "vinodotp";
                String password = "123123";
                String sender = "CAPTCH";
                String link = "http://www.smswave.in/panel/sendsms.php" + "?PhoneNumber=" + mobile + "&Text=" + message +"&user=vinodotp&password=123123&sender=CAPTCH";
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
               System.out.println(location);
                in.close();

                return sb.toString();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new String("Exception: " + e.getMessage());

            }
        } else {
            try {

                String link = IPsetting.ip+"women.php";

                String data = URLEncoder.encode("mobile", "UTF-8") + "=" +
                        URLEncoder.encode("123", "UTF-8");
                data += "&" + URLEncoder.encode("name", "UTF-8") + "=" +
                        URLEncoder.encode("mayur", "UTF-8");
                data += "&" + URLEncoder.encode("location", "UTF-8") + "=" +
                        URLEncoder.encode(location, "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" +
                        URLEncoder.encode(msg, "UTF-8");
                data += "&" + URLEncoder.encode("premsg", "UTF-8") + "=" +
                        URLEncoder.encode(premsg, "UTF-8");

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


    protected void onPreExecute(){
    }
    protected void onPostExecute(String result){
        this.statusField.setText("Login Successful");
        this.roleField.setText(result);
    }
}
