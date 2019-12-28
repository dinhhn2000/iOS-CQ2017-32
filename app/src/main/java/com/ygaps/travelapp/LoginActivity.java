package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ygaps.travelapp.API.LoginFacebookRequest;
import com.ygaps.travelapp.API.LoginFacebookResponse;
import com.ygaps.travelapp.API.LoginResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.API.LoginRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppEventsLogger.activateApp(getApplication());

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        // Check log in
        String token = sharedPreferences.getString("token", "");
        if (token != "") {
            // Move to TourList screen
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        }

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText email_phone = findViewById(R.id.email_phone);
        final EditText password = findViewById(R.id.password);
        final Button forgotPassword = findViewById(R.id.forgotPassword);
        Button signInBtn = findViewById(R.id.signInBtn);
        Button signUpBtn = findViewById(R.id.signUpBtn);

        email_phone.setText(getIntent().getStringExtra("USER_EMAIL"));
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), ForgotPassword.class);
                startActivity(intent);
            }
        });
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest = new LoginRequest(email_phone.getText().toString(), password.getText().toString());
                Call<LoginResponse> call = client.login(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Your email/phone or password is not correct", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        LoginResponse data = response.body();
                        if (data != null && data.getToken() != null) {
                            // Save token into shared preference
                            editor.putString("token", data.getToken());
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                            // Move to TourList screen
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to Register screen
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        // FACEBOOK LOGIN

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
//                    Log.d("TOKEN", "onSuccess: " + accessToken.getToken());
                    LoginFacebookRequest loginRequest = new LoginFacebookRequest(accessToken.getToken());
                    Call<LoginFacebookResponse> call = client.loginByFacebook(loginRequest);

                    call.enqueue(new Callback<LoginFacebookResponse>() {
                        @Override
                        public void onResponse(Call<LoginFacebookResponse> call, Response<LoginFacebookResponse> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LoginFacebookResponse data = response.body();
                            if (data != null && data.getToken() != null) {
                                // Save token into shared preference
                                editor.putString("token", data.getToken());
                                editor.putString("facebookAccessToken", accessToken.getToken());
                                editor.apply();
                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();

                                // Move to TourList screen
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginFacebookResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel login by Facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Button facebookLoginBtn = findViewById(R.id.facebookBtn);
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
