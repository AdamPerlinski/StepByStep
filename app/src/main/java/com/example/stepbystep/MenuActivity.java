package com.example.stepbystep;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_view);

        BottomNavigationView bottomMenu = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomMenu);
        bottomMenu.setOnNavigationItemSelectedListener(bottomListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardActivity()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()) {
                case R.id.nav_dashboard:
                    selectedFragment = new DashboardActivity();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileActivity();
                    break;
                case R.id.nav_statistics:
                    selectedFragment = new StatisticsActivity();
                    break;
                case R.id.nav_settings:
                    selectedFragment = new SettingsActivity();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}