package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ygaps.travelapp.API.TourListResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.tourListAdapter;
import com.ygaps.travelapp.utils.Tour;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourListActivity extends AppCompatActivity {

    private ArrayList<Tour> tourList = new ArrayList<>();
    private int pageNum = 1;
    private ListView lvTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        lvTour = findViewById(R.id.tourListLV);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final tourListAdapter adapter = new tourListAdapter(getApplicationContext(), R.layout.tour_list_item, tourList);
        lvTour.setAdapter(adapter);
        addTourList(builder, adapter, sharedPreferences);

        lvTour.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvTour.getLastVisiblePosition() - lvTour.getHeaderViewsCount() -
                        lvTour.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    addTourList(builder, adapter, sharedPreferences);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        Button createTourBtn = findViewById(R.id.createTourBtn);
        createTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to TourList screen
                Intent intent = new Intent(getApplication(), CreateTourActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addTourList(Retrofit.Builder builder, final tourListAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();

        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);
        int rowPerPage = 5;
        String token = sharedPreferences.getString("token", "");
        Call<TourListResponse> call = client.getTourList(token, pageNum++, rowPerPage);

        call.enqueue(new Callback<TourListResponse>() {
            @Override
            public void onResponse(Call<TourListResponse> call, Response<TourListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getTours().size() > 0) {
                        ArrayList<Tour> getData = new ArrayList<Tour>(response.body().getTours());
                        tourList.addAll(getData);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TourListResponse> call, Throwable t) {
                Log.d("Response_Error", "onFailure: " + t.getMessage());
            }
        });
    }

}
