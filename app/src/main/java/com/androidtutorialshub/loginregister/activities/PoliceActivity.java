package com.androidtutorialshub.loginregister.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialshub.loginregister.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PoliceActivity extends AppCompatActivity implements View.OnClickListener
{

    Button upload,send;
    EditText message;
    TextView path;
    public static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    String attachmentFile;
    Uri URI = null;
    String subject;
    final int RQS_LOADIMAGE = 0;
    final int RQS_SENDEMAIL = 1;

    Uri imageUri = null;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        getSupportActionBar().hide();
        upload=(Button)findViewById(R.id.upload);
        send=(Button)findViewById(R.id.send);
        message=(EditText)findViewById(R.id.message);
        path=(TextView)findViewById(R.id.path);
        final TextView path=(TextView)findViewById(R.id.path);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFolder();


                //path.setText();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
               // String name= intent.getStringExtra("name");
               String email=intent.getStringExtra("email");
                String msg=message.getText().toString();
              //  Intent womensafety=new Intent(PoliceActivity.this,HospitalActivity.class);
                //womensafety.putExtra("name",name);
              //  startActivity(womensafety);

               /*
                Intent mail=new Intent(Intent.ACTION_SEND);
                mail.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
                mail.putExtra(Intent.EXTRA_SUBJECT,"Emergency");
                String msg=message.getText().toString();
                mail.putExtra(Intent.EXTRA_TEXT,msg);
                mail.setType("message/rfc822");


                startActivity(Intent.createChooser(mail,"Choose an Email client"));

                    */
                try
                {


                    final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { email });
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
                    if (URI != null) {
                        emailIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        System.out.println(URI);

                    }
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
                    startActivity(Intent.createChooser(emailIntent,"Sending email..."));
                }
                catch (Throwable t)
                {
                    Toast.makeText(getApplicationContext(), "Request failed try again: " + t.toString(),Toast.LENGTH_LONG).show();

                }
                Intent i=getIntent();
                String username=i.getStringExtra("name");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                new PolicePortal(username,message.getText().toString(),strDate).execute();
                Toast.makeText(getApplicationContext(), "Data send successfully to police portal", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imageUri = data.getData();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            attachmentFile = cursor.getString(columnIndex);
          //  Log.e("Attachment Path:", attachmentFile);
            System.out.println(attachmentFile);
            URI = Uri.parse("file://" + imageUri.toString());
            //String s = getRealPathFromURI(URI);
            path.setText("imag11002.jpeg");
            cursor.close();
        }



    }

    public void openFolder()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void upload()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQS_LOADIMAGE);
    }
    @Override
    public void onClick(View v) {

    }
}
