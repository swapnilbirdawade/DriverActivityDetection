package com.androidtutorialshub.loginregister.activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class TouchCount extends Service implements View.OnTouchListener {

    int count=0;
    @Override
    public void onCreate()
    {
    super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_SHORT).show();
        System.out.println(count);

        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        Toast.makeText(getApplicationContext(),"Service Stopped",Toast.LENGTH_SHORT).show();
        count=0;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
          if(imm.isAcceptingText())
          {
              System.out.println("Visible");
          }
          else
              System.out.println("not visible");

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            if(imm.isAcceptingText())
            {
                System.out.println("Visible");
            }
            else
                System.out.println("not visible");

        }
        count=event.getPointerCount();
        return false;
    }
}
