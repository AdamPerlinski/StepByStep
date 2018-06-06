package com.example.stepbystep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        Intent serviceIntent = new Intent(this, KrokiService.class);
        startService(serviceIntent);
        progressBar = (ProgressBar) findViewById(R.id. progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        final Thread thread = new Thread() {

            @Override
            public void run(){
                try {
                    for (int i = 0; i < 100; i++) {
                        progressBar.setProgress(i);
                        sleep(30);  //30*100 = 3000 milliseconds = 3 sec
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent menuIntent = new Intent(SplashScreen.this, MenuActivity.class);
                    startActivity(menuIntent);
                    finish();
                }
            }
        };

        thread.start();
    }
}