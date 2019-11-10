package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.travelsupporter.API.TourListResponse;
import com.example.travelsupporter.API.Travel_Supporter_Client;
import com.example.travelsupporter.Custom_Adapter.tourListAdapter;
import com.example.travelsupporter.utils.Tour;

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

        lvTour = findViewById(R.id.tourListLV);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        final tourListAdapter adapter = new tourListAdapter(getApplicationContext(), R.layout.tour_list_item, tourList);

        addTourList(builder, adapter);


        lvTour.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvTour.getLastVisiblePosition() - lvTour.getHeaderViewsCount() -
                        lvTour.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    Toast.makeText(getApplicationContext(),"Loading...",Toast.LENGTH_SHORT).show();
                    addTourList(builder, adapter);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void addTourList(Retrofit.Builder builder, final tourListAdapter adapter) {
        Retrofit retrofit = builder.build();

        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);
        int rowPerPage = 5;
        Call<TourListResponse> call = client.getTourList(pageNum++, rowPerPage);

        call.enqueue(new Callback<TourListResponse>() {
            @Override
            public void onResponse(Call<TourListResponse> call, Response<TourListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getTours().size() > 0) {
                        ArrayList<Tour> getData = new ArrayList<Tour>(response.body().getTours());
                        tourList.addAll(getData);
                        adapter.notifyDataSetChanged();
                        lvTour.setAdapter(adapter);
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
