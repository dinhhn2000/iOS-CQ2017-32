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
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.Requests.UpdateUserInfoRequest;
import com.ygaps.travelapp.API.Responses.UserInfoResponse;

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
        final TextView userName = findViewById(R.id.userName);

        final EditText full_name = findViewById(R.id.edit_user_name);
        final EditText phone = findViewById(R.id.edit_phone);
        final EditText email = findViewById(R.id.edit_email);
        final EditText date = findViewById(R.id.edit_date);
        final EditText gender = findViewById(R.id.edit_gender);
        final Button btnUpdateUserInfo = findViewById(R.id.btn_user_edit);

        // Get token
        String token = sharedPreferences.getString("token", "");

        Call<UserInfoResponse> call = client.getUserInfo(token);

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                    if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    //Log.d("UserInfoResponse", "onResponse: Response fail - " + response.message());
                    return;
                }
                UserInfoResponse data = response.body();
                if(data == null)
                {
                    //Log.d("UserInfoResponse", "response fail " + response.message());
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
                ////Log.d("UserInfoResponse", "onResponse: Date:  " + tempDate);
                 full_name.setText(Name);
                 userName.setText(Name);
                 phone.setText(Phone);
                 email.setText(Email);
                 date.setText(tempDate);

                 if(Gender == 1)  temp = "Nam";
                 else temp = "Nữ";

                 gender.setText(temp);
                ////Log.d("UserInfoResponse", "date : " + data.getDob());
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d("UserInfoResponse", "onResponse: UserInfoResponse fail - " + t.getMessage());
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

                int temp = 0;
                String Name = full_name.getText().toString();
                String Phone = phone.getText().toString();
                String Email = email.getText().toString();
                String Date = date.getText().toString();
                String Gender = gender.getText().toString();

                if (Gender.equals("Nam") == true && Gender.equals("Nữ") == false){
                    temp = 1;
                }
                if (Gender.equals("Nam") == false && Gender.equals("Nữ") == true){
                    temp = 0;
                }
                if (Gender.equals("Nam") == false && Gender.equals("Nữ") == false)
                {
                    Toast.makeText(getApplicationContext(), "Error Gender ( " +  Gender + " ? )", Toast.LENGTH_SHORT).show();
                    return;
                }

                String Year = Date.substring(0,4);

                String Month = Date.substring(5,7);

                String Day = Date.substring(8,10);
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


                UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest(full_name.getText().toString(),email.getText().toString(),
                        phone.getText().toString(),date.getText().toString(),temp);
                // Get token
                String token = sharedPreferences.getString("token", "");

                Call<MessageResponse> call = client.updateUserInfo(token, updateUserInfoRequest);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            //Log.d("updateUserInfo", "onResponse: Update fail - " + response.message());
                            return;
                        }
                        MessageResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Update User Info Response successful", Toast.LENGTH_SHORT).show();
                            //Log.d("UpdateUserInfo", "onResponse: Update success");
                        }

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                        data.setMessage("Message : Successful");
                        Toast.makeText(getApplicationContext(), "Successful " , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        //Log.d("UpdateUserInfo", "onResponse: Update fail - " + t.getMessage());

                    }
                });
            }
        });
    }
}


