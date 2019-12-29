package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ygaps.travelapp.API.Responses.NotificationListResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notification extends AppCompatActivity {
    private int pageIndex = 1;
    private int pageSize = 5;
    private int userId = 34;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        //Intent intent = this.getIntent();
       // Bundle db = getIntent().getExtras();
       // if(db != null)
       // {
       //     UserID = intent.getIntExtra("USER_ID",3);
       // }
        final String ID = Integer.toString(userId);
        final String PageSize = Integer.toString(pageSize);
        String token = sharedPreferences.getString("token", "");
        Call<NotificationListResponse> call = client.getNotification(token,ID,pageIndex,PageSize);
        call.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {

            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {

            }
        });
    }
}
