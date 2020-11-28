package com.androidtutorialshub.loginregister.activities;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;

import com.androidtutorialshub.loginregister.R;
import com.androidtutorialshub.loginregister.adapters.UsersRecyclerAdapter;
import com.androidtutorialshub.loginregister.model.User;
import com.androidtutorialshub.loginregister.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;



public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    Button help,hospital,police,send;
    private static final int REQUEST_CODE_PERMISSION = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        getSupportActionBar().setTitle("");

        help=(Button)findViewById(R.id.womensafety);
        hospital=(Button)findViewById(R.id.hospital);
        police=(Button)findViewById(R.id.police);
        send =(Button)findViewById(R.id.speed);

        String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                      String name= intent.getStringExtra("name");
                      String email=intent.getStringExtra("EMAIL");
                Intent womensafety=new Intent(UsersListActivity.this, com.androidtutorialshub.loginregister.Map.MapsActivity.class);
                       womensafety.putExtra("name",name);
                       womensafety.putExtra("email",email);
                startActivity(womensafety);
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String name= intent.getStringExtra("name");
                String email=intent.getStringExtra("EMAIL");
                Intent police=new Intent(UsersListActivity.this,PoliceActivity.class);
                police.putExtra("name",name);
                police.putExtra("email",email);
                startActivity(police);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String name= intent.getStringExtra("name");
                String email=intent.getStringExtra("EMAIL");
                String parent_mobile=intent.getStringExtra("parent_mobile");
                Intent womensafety=new Intent(UsersListActivity.this,DriverSafetyActivity.class);
                womensafety.putExtra("name",name);
                womensafety.putExtra("email",email);
                womensafety.putExtra("parent_mobile",parent_mobile);
                startActivity(womensafety);
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=getIntent();
                String name= intent.getStringExtra("name");
                String email=intent.getStringExtra("EMAIL");
                String parent_mobile=intent.getStringExtra("parent_mobile");
                Intent speed=new Intent(UsersListActivity.this,MainActivity.class);
                speed.putExtra("name",name);
                speed.putExtra("email",email);
                //speed.putExtra("parent_mobile",parent_mobile);
                startActivity(speed);

            }
        });



        initViews();
        initObjects();

    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
       //recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
       // recyclerViewUsers.setLayoutManager(mLayoutManager);
      //  recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
       // recyclerViewUsers.setHasFixedSize(true);
      //  recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(databaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
