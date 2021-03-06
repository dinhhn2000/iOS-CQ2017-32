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
import com.ygaps.travelapp.Custom_Adapter.TourCommentAdapter;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.utils.TourComment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourCommentListActivity extends AppCompatActivity {

    private ArrayList<TourComment> comments = new ArrayList<>();
    private ListView lvTourComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_comment_list);

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
        lvTourComment = findViewById(R.id.tourCommentListLV);
        lvTourComment.setEmptyView(findViewById(R.id.emptyView));

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final TourCommentAdapter adapter = new TourCommentAdapter(getApplicationContext(), R.layout.tour_comment_item, comments);
        lvTourComment.setAdapter(adapter);
        getCommentList(builder, adapter, sharedPreferences);

    }

    private void getCommentList(Retrofit.Builder builder, final TourCommentAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();
        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        String token = sharedPreferences.getString("token", "");
        long tourId = getIntent().getLongExtra("TOUR_ID", 0);
//       //Log.d("service", "onClick: " + serviceId);

        Call<getTourInfoResponse> call = client.getTour(token, tourId);
        call.enqueue(new Callback<getTourInfoResponse>() {
            @Override
            public void onResponse(Call<getTourInfoResponse> call, Response<getTourInfoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getTourComments().size() > 0) {
                        ArrayList<TourComment> getData = new ArrayList<>(response.body().getTourComments());
//                       //Log.d("zzz", "onResponse: " + response.body().getTourComments().toString());
                        comments.addAll(getData);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTourInfoResponse> call, Throwable t) {
               //Log.d("Response_Error", "onFailure: " + t.getMessage());
            }
        });
    }

}
