package com.ygaps.travelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.API.Responses.StopPointFeedbackResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.Custom_Adapter.StopPointFeedbackAdapter;
import com.ygaps.travelapp.utils.StopPointFeedback;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StopPointFeedbackListActivity extends AppCompatActivity {

    private ArrayList<StopPointFeedback> feedbacks = new ArrayList<>();
    private ListView lvFeedback;
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_feedback_list);

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
        lvFeedback = findViewById(R.id.stopPointFeedbackListLV);
        lvFeedback.setEmptyView(findViewById(R.id.emptyView));

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        final StopPointFeedbackAdapter adapter = new StopPointFeedbackAdapter(getApplicationContext(), R.layout.stop_point_feedback_item, feedbacks);
        lvFeedback.setAdapter(adapter);
        getFeedbackList(builder, adapter, sharedPreferences);

        lvFeedback.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvFeedback.getLastVisiblePosition() - lvFeedback.getHeaderViewsCount() -
                        lvFeedback.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    // Now your listview has hit the bottom
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    getFeedbackList(builder, adapter, sharedPreferences);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void getFeedbackList(Retrofit.Builder builder, final StopPointFeedbackAdapter adapter, SharedPreferences sharedPreferences) {
        Retrofit retrofit = builder.build();
        Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        String token = sharedPreferences.getString("token", "");
        long serviceId = getIntent().getLongExtra("SERVICE_ID", 0);
        Log.d("service", "onClick: " + serviceId);
        int rowPerPage = 5;

        Call<StopPointFeedbackResponse> call = client.getStopPointFeedback(token, serviceId, pageNum++, rowPerPage);
        call.enqueue(new Callback<StopPointFeedbackResponse>() {
            @Override
            public void onResponse(Call<StopPointFeedbackResponse> call, Response<StopPointFeedbackResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getFeedbackList().size() > 0) {
                        Log.d("Feedbacks", "onResponse: " + response.body().getFeedbackList());
                        ArrayList<StopPointFeedback> getData = new ArrayList<>(response.body().getFeedbackList());
                        feedbacks.addAll(getData);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<StopPointFeedbackResponse> call, Throwable t) {
                Log.d("Response_Error", "onFailure: " + t.getMessage());
            }
        });
    }

}
