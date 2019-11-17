package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelsupporter.API.RegisterRequest;
import com.example.travelsupporter.API.RegisterResponse;
import com.example.travelsupporter.API.Travel_Supporter_Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);


        final EditText password = findViewById(R.id.password);
        final EditText full_name = findViewById(R.id.fullName);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);

        Button okRegisterInBtn = findViewById(R.id.okRegisterInBtn);
        Button backRegisterInBtn = findViewById(R.id.backRegisterInBtn);

        okRegisterInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRequest registerRequest = new RegisterRequest(password.getText().toString(), full_name.getText().toString(),
                        email.getText().toString(), phone.getText().toString());

                Call<RegisterResponse> call = client.register(registerRequest);

                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RegisterResponse data = response.body();

                        if (data != null && data.getEmail() != null ) {
                            Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show();

                            // Move to Login screen
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            intent.putExtra("USER_EMAIL", data.getEmail());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        backRegisterInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void creattePost(){
      //  RegisterRequest registerRequest = new RegisterRequest();
    }
}
