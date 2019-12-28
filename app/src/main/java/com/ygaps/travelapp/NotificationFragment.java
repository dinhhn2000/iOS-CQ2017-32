package com.ygaps.travelapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.ygaps.travelapp.API.NotificationListResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.notificationAdapter;
import com.ygaps.travelapp.utils.Notification;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    private ArrayList<Notification> notification = new ArrayList<>();
    private int pageNum = 1;
    private ListView lvNotification;
    private int pageSize = 5;
    private int userId = 34;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);

        return view;
    }
    private void addNotificationList(Retrofit.Builder builder, final notificationAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();


    }
}
