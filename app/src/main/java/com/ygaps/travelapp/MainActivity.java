package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.ygaps.travelapp.ListFragment;
import com.ygaps.travelapp.FutureFragment;
import com.ygaps.travelapp.MapFragment;
import com.ygaps.travelapp.NotificationFragment;
import com.ygaps.travelapp.SettingFragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //openFragment(ListFragment.newInstance("", ""));
        Intent intent6 = new Intent(getApplication(), SettingActivity.class);
        startActivity(intent6);

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.navigation_tour:
                            Intent intent5 = new Intent(getApplication(), UserActivity.class);
                            startActivity(intent5);
                            return true;
                        case R.id.navigation_future:
                            Intent intent4 = new Intent(getApplication(), SettingActivity.class);
                            startActivity(intent4);
                            //openFragment(FutureFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_map:
                            Intent intent3 = new Intent(getApplication(), SettingActivity.class);
                            startActivity(intent3);
                            // openFragment(MapFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_notification:
                            Intent intent2 = new Intent(getApplication(), SettingActivity.class);
                            startActivity(intent2);
                            //openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_setting:
                            Intent intent1 = new Intent(getApplication(), SettingActivity.class);
                            startActivity(intent1);
                            return true;
                    }
                    return false;
                }

            };


}