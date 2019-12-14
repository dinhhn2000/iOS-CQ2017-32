package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelsupporter.API.CreateTourResponse;
import com.example.travelsupporter.API.Travel_Supporter_Client;
import com.example.travelsupporter.API.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText full_name = findViewById(R.id.edit_user_name);
        final EditText phone = findViewById(R.id.edit_phone);
        final EditText email = findViewById(R.id.edit_email);
        final EditText date = findViewById(R.id.edit_date);
        final EditText gender = findViewById(R.id.edit_gender);
        // Get token
        String token = sharedPreferences.getString("token", "");

        Call<UserInfoResponse> call = client.getUserInfo(token);
        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("UserInfoResponse", "onResponse: Response fail - " + response.message());
                    return;
                }
                UserInfoResponse data = response.body();

                //Log.d("UserInfoResponse", "data fullname : " + data.getFull_name());
                if(data == null)
                {
                    Log.d("UserInfoResponse", "response fail " + response.message());
                    return;
                }
                String Name = "";
                String Phone = "";
                String Email = "";
                String Date= "";
                int Gender;
                String temp = "";

                Name = data.getFull_name();
                Phone = data.getPhone();
                Email = data.getEmail();
                Date = data.getDob();
                Gender = data.getGender();

                 full_name.setText(Name);
                 phone.setText(Phone);
                 email.setText(Email);
                 date.setText(Date);
                 if(Gender == 1)  temp = "Nam";
                 else temp = "Nữ";
                 gender.setText(temp);
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("UserInfoResponse", "onResponse: UserInfoResponse fail - " + t.getMessage());
            }
        });
    }
}
