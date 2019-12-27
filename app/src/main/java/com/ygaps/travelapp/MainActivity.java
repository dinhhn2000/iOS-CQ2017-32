package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private TourListFragment tourListFragment;
    private FutureFragment futureFragment;
    private MapFragment mapFragment;
    private NotificationFragment notificationFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.navigation) ;
        mMainNav= findViewById(R.id.navigation);

        tourListFragment = new TourListFragment();
        futureFragment = new FutureFragment();
        mapFragment = new MapFragment();
        notificationFragment = new NotificationFragment();
        settingFragment = new SettingFragment();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_tour:
                        setFrament(tourListFragment);
                        return true;
                    case R.id.navigation_future:
                        setFrament(futureFragment);
                        return true;
                    case R.id.navigation_map:
                        setFrament(mapFragment);
                        return true;
                    case R.id.navigation_notification:
                        setFrament(notificationFragment);

                        return true;
                    case R.id.navigation_setting:
                        setFrament(settingFragment);
                        return true;
                }
                return false;
            }
        });

    }
private void setFrament(Fragment frament){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,frament);
        fragmentTransaction.commit();
}
}