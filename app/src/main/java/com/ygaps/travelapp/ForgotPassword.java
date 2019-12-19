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

import com.ygaps.travelapp.API.LoginResponse;
import com.ygaps.travelapp.API.MessageResponse;
import com.ygaps.travelapp.API.RequestOTP_PasswordRecoveryRequest;
import com.ygaps.travelapp.API.RequestOTP_PasswordRecoveryResponse;
import com.ygaps.travelapp.API.SendEmailVerificationRequest;
import com.ygaps.travelapp.API.SendEmailVerificationResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.UpdateUserInfoRequest;
import com.ygaps.travelapp.API.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText enter_email = findViewById(R.id.enterEmail);
        final Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = "email";

                RequestOTP_PasswordRecoveryRequest requestOTP_passwordRecoveryRequest = new RequestOTP_PasswordRecoveryRequest(temp,enter_email.getText().toString());
                Call<RequestOTP_PasswordRecoveryResponse> call = client.RequestOTP(requestOTP_passwordRecoveryRequest);
                if (enter_email.getText().toString() == ""  ) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address ", Toast.LENGTH_SHORT).show();
                    return;
                }

                call.enqueue(new Callback<RequestOTP_PasswordRecoveryResponse>() {
                    @Override
                    public void onResponse(Call<RequestOTP_PasswordRecoveryResponse> call, Response<RequestOTP_PasswordRecoveryResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Request successfully sent" , Toast.LENGTH_SHORT).show();
                            Log.d("SendEmailVerification", "onResponse: Send email verification successfull - " + response.message());
                            return;
                        }
                        RequestOTP_PasswordRecoveryResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Send email verification successful", Toast.LENGTH_SHORT).show();
                            Log.d("SendEmailVerification", "onResponse: Send email verification success");

                            int UserID = data.getUserId();
                            String temp = data.getType();

                            Call<SendEmailVerificationResponse> call1 = client.GetEmailVerification( UserID,temp);
                            call1.enqueue(new Callback<SendEmailVerificationResponse>() {
                                @Override
                                public void onResponse(Call<SendEmailVerificationResponse> call1, Response<SendEmailVerificationResponse> response) {
                                    if (response.isSuccessful()) {
                                        SendEmailVerificationResponse data = response.body();
                                        Log.d("SendEmailVerification", "onResponse: Send email verification succesful - " + data.getUserId() +" "+ data.getSendTo() +" "+ data.getExpiredOn());
                                    }
                                }
                                @Override
                                public void onFailure(Call<SendEmailVerificationResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Send email verification fail " , Toast.LENGTH_SHORT).show();
                                    Log.d("Response_Error", "onFailure: " + t.getMessage());
                                }
                            });

                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestOTP_PasswordRecoveryResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Request send email verification fail " , Toast.LENGTH_SHORT).show();
                        Log.d("SendEmailVerification", "onResponse: Request Send email verification fail - " + t.getMessage());
                    }
                });

            }
        });

    }
}
