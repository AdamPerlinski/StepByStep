package com.example.stepbystep;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class KrokiService extends Service implements SensorEventListener, StepListener {
    private TextView  kroki;
    private Sensor Acceletometer;
    private SensorManager SM;
    private int iterator;
    Intent intent = new Intent("TEST");
    private int Pauser;
    private  int Kroki;
    private int iter;
    private WykrywaczKroku Instancja;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *  Used to name the worker thread, important only for debugging.
     */


        private void writeToLogs(String message) {
            Log.d("HelloServices", message);



        }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        writeToLogs("Called onStartCommand() methond");


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        SM = (SensorManager) getSystemService((
                Context.SENSOR_SERVICE));
        Acceletometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this, Acceletometer, SensorManager.SENSOR_DELAY_FASTEST);
        Instancja = new WykrywaczKroku();
        Instancja.registerListener(this);

        writeToLogs("Called onCreate() method.");

    }

    @Override
    public void onDestroy() {
        writeToLogs("Called onDestroy() method");
        super.onDestroy();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Instancja.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }




    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



    @Override
    public void step(long czas) {
        Kroki++;
        writeToLogs(String.valueOf(Kroki));
        intent.putExtra("steps",String.valueOf(Kroki));
        sendBroadcast(intent);

    }

    public  int getstep(){
        return Kroki;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
