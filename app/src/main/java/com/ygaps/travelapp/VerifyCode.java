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

import com.facebook.login.Login;
import com.ygaps.travelapp.API.SendEmailVerificationResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.VerifyCode_EmailVerificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerifyCode extends AppCompatActivity {
    private int UserID;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);


        final EditText enter_Code = findViewById(R.id.enterCode);
        final Button submit = findViewById(R.id.submitCode);

        Intent intent = this.getIntent();
        Bundle db = getIntent().getExtras();
        if(db != null)
        {
            UserID = intent.getIntExtra("USER_ID",3);
            type = intent.getStringExtra("USER_TYPE");
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<VerifyCode_EmailVerificationResponse> call = client.VerifyCode(UserID,type,enter_Code.getText().toString());
                call.enqueue(new Callback<VerifyCode_EmailVerificationResponse>() {
                    @Override
                    public void onResponse(Call<VerifyCode_EmailVerificationResponse> call, Response<VerifyCode_EmailVerificationResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verify code fail" , Toast.LENGTH_SHORT).show();
                            Log.d("VerifyCode", "onResponse: Verify code successfull - " + response.message());
                            return;
                        }

                        VerifyCode_EmailVerificationResponse data = response.body();

                        if (data != null) {
                            Toast.makeText(getApplicationContext(), "Verify code successful", Toast.LENGTH_SHORT).show();
                            Log.d("VerifyCode", "onResponse: Verify code success "+ data.getSuccess() );
                        }
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        //intent.putExtra("USER_ID", data.getUserId());
                        //intent.putExtra("USER_TYPE", type);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<VerifyCode_EmailVerificationResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Send verify code fail " , Toast.LENGTH_SHORT).show();
                        Log.d("VerifyCode", "onResponse: Send verify code fail - " + t.getMessage());
                    }
                });
            }
        });
    }
}
