package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private TourListFragment tourListFragment;
    private HistoryTourListFragment HistoryTourListFragment;
    private MapFragment mapFragment;
    private NotificationFragment notificationFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check log in
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if (token == "") {
            // Move to TourList screen
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
        }

        mMainFrame = (FrameLayout) findViewById(R.id.navigation);
        mMainNav = findViewById(R.id.navigation);
//        Set personal tour list as default fragment
//        mMainNav.setSelectedItemId(R.id.navigation_tour);

        tourListFragment = new TourListFragment();
        HistoryTourListFragment = new HistoryTourListFragment();
        mapFragment = new MapFragment();
        notificationFragment = new NotificationFragment();
        settingFragment = new SettingFragment();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Log.d("initial_fragment", "onNavigationItemSelected: " + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.navigation_tour:
                        setFragment(tourListFragment);
                        return true;
                    case R.id.navigation_future:
                        setFragment(HistoryTourListFragment);
                        return true;
                    case R.id.navigation_map:
                        setFragment(mapFragment);
                        return true;
                    case R.id.navigation_notification:
                        setFragment(notificationFragment);
                        return true;
                    case R.id.navigation_setting:
                        setFragment(settingFragment);
                        return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            mMainNav.setSelectedItemId(R.id.navigation_tour); // change to whichever id should be default
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}