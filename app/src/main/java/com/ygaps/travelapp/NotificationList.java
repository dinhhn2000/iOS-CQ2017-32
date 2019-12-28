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
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ygaps.travelapp.API.TourListResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.notificationAdapter;
import com.ygaps.travelapp.NotificationList;
import com.ygaps.travelapp.API.NotificationListResponse;
import java.util.ArrayList;
import com.ygaps.travelapp.utils.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationList extends AppCompatActivity {
    private ArrayList<Notification> notification = new ArrayList<>();
    private int pageNum = 1;
    private ListView lvNotification;
    private int pageSize = 5;
    private int userId = 34;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        lvNotification = findViewById(R.id.notificationListLV);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final notificationAdapter adapter = new notificationAdapter(getApplicationContext(), R.layout.activity_notification, notification);
        lvNotification.setAdapter(adapter);
        addNotificationList(builder, adapter, sharedPreferences);



        lvNotification.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvNotification.getLastVisiblePosition() - lvNotification.getHeaderViewsCount() -
                        lvNotification.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    addNotificationList(builder, adapter, sharedPreferences);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void addNotificationList(Retrofit.Builder builder, final notificationAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();

        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);
        int rowPerPage = 5;
        String token = sharedPreferences.getString("token", "");
        String ID = Integer.toString(userId);
        String PageSize = Integer.toString(pageSize);
        Call<NotificationListResponse> call = client.getNotification(token,ID, pageNum++, PageSize);

        call.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getNotification().size() > 0) {
                        ArrayList<Notification> getData = new ArrayList<Notification>(response.body().getNotification());
                        notification.addAll(getData);
                        adapter.notifyDataSetChanged();
                        Log.d("Response_Notification", "Successful ");
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                Log.d("Response_Notification", "onFailure: " + t.getMessage());
            }
        });
    }

}

