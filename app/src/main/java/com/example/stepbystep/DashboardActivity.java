package com.example.stepbystep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DashboardActivity extends Fragment {
    TextView kroki;
   private  BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            kroki = (TextView) getView().findViewById(R.id.kroki);
            String nowPlaying = intent.getStringExtra("steps");
            kroki.setText("" + nowPlaying);

            //or
            //exercises = ParseJSON.ChallengeParseJSON(intent.getStringExtra(MY_KEY));

        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);


        return inflater.inflate(R.layout.dashboard_view, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, new IntentFilter("TEST"));
    }
    @Override
    public void onPause(){
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }
}