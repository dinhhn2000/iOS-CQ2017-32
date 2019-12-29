package com.ygaps.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.API.Responses.TourListResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.TourListAdapter;
import com.ygaps.travelapp.utils.Tour;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalTourList extends AppCompatActivity {

    private ArrayList<Tour> tourList = new ArrayList<>();
    private int pageIndex = 1;
    private ListView lvTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tour_list);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        lvTour = findViewById(R.id.personalTourListLV);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final TourListAdapter adapter = new TourListAdapter(getApplicationContext(), R.layout.tour_list_item, tourList);
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

        lvTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), tourList.get(position).toString(), Toast.LENGTH_SHORT).show();
                Log.d("personal", "onItemClick: " + tourList.get(position).toString());
                Intent intent = new Intent(getBaseContext(), AddStopPointActivity.class);
                intent.putExtra("TOUR_ID", tourList.get(position).getId());
                startActivity(intent);

            }
        });
    }

    private void addTourList(Retrofit.Builder builder, final TourListAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();

        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);
        int rowPerPage = 5;
        String token = sharedPreferences.getString("token", "");
        Call<TourListResponse> call = client.getPersonalTourList(token, pageIndex++, rowPerPage);

        call.enqueue(new Callback<TourListResponse>() {
            @Override
            public void onResponse(Call<TourListResponse> call, Response<TourListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getTours().size() > 0) {
                        ArrayList<Tour> getData = new ArrayList<Tour>(response.body().getTours());
                        Log.d("PERSONAL_TOUR", "onResponse: " + getData);
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
