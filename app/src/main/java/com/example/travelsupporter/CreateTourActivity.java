package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.travelsupporter.API.CreateTourRequest;
import com.example.travelsupporter.API.CreateTourResponse;
import com.example.travelsupporter.API.Travel_Supporter_Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateTourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText tour_name = findViewById(R.id.tourName);
        final EditText start_time = findViewById(R.id.startDate);
        final EditText end_time = findViewById(R.id.endDate);
        final RadioButton is_private = findViewById(R.id.isPrivate);
        final EditText adults = findViewById(R.id.adults);
        final EditText children = findViewById(R.id.children);
        final EditText minCost = findViewById(R.id.minCost);
        final EditText maxCost = findViewById(R.id.maxCost);
        final EditText avartar = findViewById(R.id.avartar);
        Button okCreateTourInBtn = findViewById(R.id.okCreateTourInBtn);

        okCreateTourInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTourRequest createTourRequest = new CreateTourRequest(tour_name.getText().toString(), start_time.getText().toString(), end_time.getText().toString(), is_private.isChecked(),
                        adults.getText().toString(), children.getText().toString(), minCost.getText().toString(), maxCost.getText().toString(), avartar.getText().toString());
                Call<CreateTourResponse> call = client.createtour(createTourRequest);
                call.enqueue(new Callback<CreateTourResponse>() {
                    @Override
                    public void onResponse(Call<CreateTourResponse> call, Response<CreateTourResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<CreateTourResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
