package com.ygaps.travelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.API.Responses.getTourInfoResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.stopPointAdapter;
import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopPointListActivity extends AppCompatActivity {

    private ArrayList<StopPoint> stopPointList = new ArrayList<>();
    private ListView lvStopPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        lvStopPoint = findViewById(R.id.stopPointListLV);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final stopPointAdapter adapter = new stopPointAdapter(getApplicationContext(), R.layout.stop_point_item, stopPointList);
        lvStopPoint.setAdapter(adapter);
        addTourList(builder, adapter, sharedPreferences);

//        lvStopPoint.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
//                        && (lvStopPoint.getLastVisiblePosition() - lvStopPoint.getHeaderViewsCount() -
//                        lvStopPoint.getFooterViewsCount()) >= (adapter.getCount() - 1)) {
//
//                    // Now your listview has hit the bottom
//                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
//                    addTourList(builder, adapter, sharedPreferences);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
    }

    private void addTourList(Retrofit.Builder builder, final stopPointAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();
        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        String token = sharedPreferences.getString("token", "");
        long tourId = getIntent().getLongExtra("TOUR_ID", 0);

        Call<getTourInfoResponse> call = client.getTour(token,tourId);
        call.enqueue(new Callback<getTourInfoResponse>() {
            @Override
            public void onResponse(Call<getTourInfoResponse> call, Response<getTourInfoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStopPoints().size() > 0) {
                        ArrayList<StopPoint> getData = new ArrayList<>(response.body().getStopPoints());
                        stopPointList.addAll(getData);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTourInfoResponse> call, Throwable t) {
                Log.d("Response_Error", "onFailure: " + t.getMessage());
            }
        });
    }

}
