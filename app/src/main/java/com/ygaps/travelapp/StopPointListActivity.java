package com.ygaps.travelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.API.Responses.getTourInfoResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.StopPointAdapter;
import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopPointListActivity extends AppCompatActivity {

    private ArrayList<StopPoint> stopPointList = new ArrayList<>();
    private ArrayList<StopPoint> sourceStopPointList = new ArrayList<>();
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
        lvStopPoint.setEmptyView(findViewById(R.id.emptyView));

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final StopPointAdapter adapter = new StopPointAdapter(this, R.layout.stop_point_item, stopPointList);
        lvStopPoint.setAdapter(adapter);
        getStopPointList(builder, adapter, sharedPreferences);

        final TextView searchEditText = findViewById(R.id.stopPointSearchEditText);
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchStr = searchEditText.getText().toString();
                    ArrayList<StopPoint> searchResult = searchStopPoints(searchStr);
                    if(searchResult != null){
                        stopPointList.clear();
                        stopPointList.addAll(searchResult);
                       //Log.d("SEARCH", "onEditorAction: "+ stopPointList.toString());
                        adapter.notifyDataSetChanged();
                    }
                    return true;
                }
                return false;
            }
        });
    }



    public ArrayList<StopPoint> searchStopPoints(String name){
        ArrayList<StopPoint> result = new ArrayList<>();
        for(int i = 0; i < sourceStopPointList.size(); i++){
            if(sourceStopPointList.get(i).getName().contains(name))
                result.add(sourceStopPointList.get(i));
        }
        if(result.size() == 0)
            return null;
        return result;
    }

    private void getStopPointList(Retrofit.Builder builder, final StopPointAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();
        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        String token = sharedPreferences.getString("token", "");
        long tourId = getIntent().getLongExtra("TOUR_ID", 0);

        Call<getTourInfoResponse> call = client.getTour(token, tourId);
        call.enqueue(new Callback<getTourInfoResponse>() {
            @Override
            public void onResponse(Call<getTourInfoResponse> call, Response<getTourInfoResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStopPoints().size() > 0) {
                        ArrayList<StopPoint> getData = new ArrayList<>(response.body().getStopPoints());
                        stopPointList.clear();
                        stopPointList.addAll(getData);
                        sourceStopPointList.addAll(getData);
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
