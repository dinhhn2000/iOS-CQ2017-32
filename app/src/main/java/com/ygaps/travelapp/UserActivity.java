package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ygaps.travelapp.API.MessageResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.UpdateUserInfoRequest;
import com.ygaps.travelapp.API.UserInfoResponse;

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
        final Button btnUpdateUserInfo = findViewById(R.id.btn_user_edit);
        final Button btnBack = findViewById(R.id.btn_user_back);
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
                if(data == null)
                {
                    Log.d("UserInfoResponse", "response fail " + response.message());
                    return;
                }
                String Name = "";
                String Phone = "";
                String Email = "";
                String Date = "";
                int Gender;
                String temp = "";

                Name = data.getFull_name();
                Phone = data.getPhone();
                Email = data.getEmail();
                Date = data.getDob();
                Gender = data.getGender();

                String tempDate = Date.substring(0,10);
                //Log.d("UserInfoResponse", "onResponse: Date:  " + tempDate);
                 full_name.setText(Name);
                 phone.setText(Phone);
                 email.setText(Email);
                 date.setText(tempDate);

                 if(Gender == 1)  temp = "Nam";
                 else temp = "Nữ";

                 gender.setText(temp);
                //Log.d("UserInfoResponse", "date : " + data.getDob());
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("UserInfoResponse", "onResponse: UserInfoResponse fail - " + t.getMessage());
            }

        });
        btnUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (full_name.getText().toString() == "" || phone.getText().toString() == "" || email.getText().toString() == "" ||
                        date.getText().toString() == "" || gender.getText().toString() == "" ) {
                    Toast.makeText(getApplicationContext(), "Please fill the other fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String Name = "";
                String Phone = "";
                String Email = "";
                String Date = "";
                String Gender = "";
                int temp = 0;

                Name = full_name.getText().toString();
                Phone = phone.getText().toString();
                Email = email.getText().toString();
                Date = date.getText().toString();
                Gender = gender.getText().toString();
               // Log.d("updateUserInfo", "onResponse: before - " + temp);
               // Log.d("updateUserInfo", "onResponse: before - " + gender.getText().length());



                if (Gender.equals("Nam") == true && Gender.equals("Nữ") == false){
                    temp = 1;
                }
                if (Gender.equals("Nam") == false && Gender.equals("Nữ") == true){
                    temp = 0;
                }
                if (Gender.equals("Nam") == false && Gender.equals("Nữ") == false)
                {
                    Toast.makeText(getApplicationContext(), "Error Gender ( " +  Gender + " ? )", Toast.LENGTH_SHORT).show();
                    //Log.d("updateUserInfo", "onResponse: gender: " + Gender + " ?");
                    return;
                }

                String Year = Date.substring(0,4);
                //Log.d("updateUserInfo", "onResponse: Year - " + Year);

                String Month = Date.substring(5,7);
                //Log.d("updateUserInfo", "onResponse: Month - " + Month);

                String Day = Date.substring(8,10);
                //Log.d("updateUserInfo", "onResponse: Day - " + Day);
                //Log.d("updateUserInfo", "onResponse: before - " + gender.getText().toString());
                if(Date.length() != 10 ) {
                    Toast.makeText(getApplicationContext(), "Error dob (required format: YYYY-MM-DD )", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Year.matches("\\d+"))
                {
                    Toast.makeText(getApplicationContext(), "Error dob (Year is number )", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Month.matches("\\d+"))
                {
                    Toast.makeText(getApplicationContext(), "Error dob (Month is number )", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Day.matches("\\d+"))
                {
                    Toast.makeText(getApplicationContext(), "Error dob (Day is number )", Toast.LENGTH_SHORT).show();
                    return;
                }

                full_name.setText(Name);
                phone.setText(Phone);
                email.setText(Email);
                date.setText(Date);
                gender.setText(Gender);
                //Log.d("updateUserInfo", "onResponse: Update temp fullname - " + Name);
                //Log.d("updateUserInfo", "onResponse: Update temp phone numbber - " + Phone);
                //Log.d("updateUserInfo", "onResponse: Update temp email - " + Email);
                //Log.d("updateUserInfo", "onResponse: Update temp date - " + Date);
                //Log.d("updateUserInfo", "onResponse: Update temp gender - " + temp);

                //Log.d("updateUserInfo", "onResponse: Update fullname - " + full_name.getText().toString());
                //Log.d("updateUserInfo", "onResponse: Update phone numbber - " + phone.getText().toString());
                //Log.d("updateUserInfo", "onResponse: Update email - " + email.getText().toString());
                //Log.d("updateUserInfo", "onResponse: Update date - " + date.getText().toString());
                //Log.d("updateUserInfo", "onResponse: Update gender - " + gender.getText().toString());
                //Integer.parseInt(gender.getText().toString()

                UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest(full_name.getText().toString(),email.getText().toString(),phone.getText().toString(),date.getText().toString(),temp);
                // Get token
                String token = sharedPreferences.getString("token", "");

                Call<MessageResponse> call = client.updateUserInfo(token, updateUserInfoRequest);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d("updateUserInfo", "onResponse: Update fail - " + response.message());
                            return;
                        }
                        MessageResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Update User Info Response successful", Toast.LENGTH_SHORT).show();
                            Log.d("UpdateUserInfo", "onResponse: Update success");
                        }
                        Intent intent = new Intent(getApplication(), UserActivity.class);
                        startActivity(intent);
                        data.setMessage("Message : Successful");
                        Toast.makeText(getApplicationContext(), "Successful " , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("UpdateUserInfo", "onResponse: Update fail - " + t.getMessage());

                    }
                });

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

}


