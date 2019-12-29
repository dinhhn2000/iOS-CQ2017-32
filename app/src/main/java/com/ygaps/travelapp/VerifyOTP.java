package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.Requests.VerifyOTP_PasswordRecoveryRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyOTP extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 100;
    private int temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText enter_password = findViewById(R.id.enterNewPassword);
        final EditText enter_OTP = findViewById(R.id.enterCodeOTP);
        final Button submit = findViewById(R.id.submit);

        Intent intent = this.getIntent();
         Bundle db = getIntent().getExtras();
         if(db != null)
         {
              temp = intent.getIntExtra("UserId",3);
         }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyOTP_PasswordRecoveryRequest verifyOTP_passwordRecoveryRequest = new VerifyOTP_PasswordRecoveryRequest(temp,enter_password.getText().toString(),enter_OTP.getText().toString());
                Call<MessageResponse> call = client.RecoveryPassword(verifyOTP_passwordRecoveryRequest);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verify OTP fail" , Toast.LENGTH_SHORT).show();
                            Log.d("VerifyOTP", "onResponse: Verify OTP successfull - " + response.message());
                            return;
                        }

                        MessageResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Verify OTP successful", Toast.LENGTH_SHORT).show();
                            Log.d("VerifyOTP", "onResponse: Verify OTP success"+ data.getMessage());
                        }
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Verify OTP fail " , Toast.LENGTH_SHORT).show();
                        Log.d("VerifyOTP", "onResponse: Verify OTP fail - " + t.getMessage());
                    }
                });
            }
        });
    }
}
