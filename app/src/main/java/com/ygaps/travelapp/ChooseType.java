package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ygaps.travelapp.API.MessageResponse;
import com.ygaps.travelapp.API.SendEmailVerificationRequest;
import com.ygaps.travelapp.API.SendEmailVerificationResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.VerifyOTP_PasswordRecoveryRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseType extends AppCompatActivity {

    private int UsedId;
    private String Email;
    private String Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final Button email = findViewById(R.id.verifyEmail);
        final Button phone = findViewById(R.id.verifyPhone);

        Intent intent = this.getIntent();
        Bundle db = getIntent().getExtras();
        if(db != null)
        {

            UsedId = intent.getIntExtra("USER_ID",3);
            Email = intent.getStringExtra("USER_EMAIL");
            Phone = intent.getStringExtra("USER_PHONE");
        }
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String type = "email";

                Call<SendEmailVerificationResponse> call = client.GetEmailVerification(UsedId,type);
            call.enqueue(new Callback<SendEmailVerificationResponse>() {
                @Override
                public void onResponse(Call<SendEmailVerificationResponse> call, Response<SendEmailVerificationResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Verify OTP fail" , Toast.LENGTH_SHORT).show();
                        Log.d("VerifyOTP", "onResponse: Verify OTP successfull - " + response.message());
                        return;
                    }

                    SendEmailVerificationResponse data = response.body();

                    if (data != null) {
                        Toast.makeText(getApplicationContext(), "Verify OTP successful", Toast.LENGTH_SHORT).show();
                        Log.d("VerifyOTP", "onResponse: Verify OTP success"+ data.getUserId() + " " + data.getSendTo() + " " + data.getExpiredOn());
                    }
                    Intent intent = new Intent(getApplication(), VerifyCode.class);
                    intent.putExtra("USER_ID", data.getUserId());
                    intent.putExtra("USER_TYPE", type);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<SendEmailVerificationResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Send verify code fail " , Toast.LENGTH_SHORT).show();
                    Log.d("VerifyCode", "onResponse: Send verify code fail - " + t.getMessage());
                }
            });
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String type = "phone";
                Call<SendEmailVerificationResponse> call = client.GetEmailVerification(UsedId,type);
                call.enqueue(new Callback<SendEmailVerificationResponse>() {
                    @Override
                    public void onResponse(Call<SendEmailVerificationResponse> call, Response<SendEmailVerificationResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verify OTP fail" , Toast.LENGTH_SHORT).show();
                            Log.d("VerifyOTP", "onResponse: Verify OTP successfull - " + response.message());
                            return;
                        }

                        SendEmailVerificationResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Verify OTP successful", Toast.LENGTH_SHORT).show();
                            Log.d("VerifyOTP", "onResponse: Verify OTP success"+ data.getUserId() + " " + data.getSendTo() + " " + data.getExpiredOn());
                        }
                        Intent intent = new Intent(getApplication(), VerifyCode.class);
                        intent.putExtra("USER_ID", data.getUserId());
                        intent.putExtra("USER_TYPE", type);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<SendEmailVerificationResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Send verify code fail " , Toast.LENGTH_SHORT).show();
                        Log.d("VerifyCode", "onResponse: Send verify code fail - " + t.getMessage());
                    }
                });
            }
        });

    }
}
