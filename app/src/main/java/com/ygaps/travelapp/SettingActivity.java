package com.ygaps.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

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
                            Intent intent5 = new Intent(getApplication(), PersonalTourList.class);
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
                            Intent intent2 = new Intent(getApplication(), NotificationList.class);
                            startActivity(intent2);

                            return true;

                    }
                    return false;
                }

            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);



        final TextView edit_profile = findViewById(R.id.editProfile);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), UserActivity.class);
                startActivity(intent);
            }
        });

    }
}
