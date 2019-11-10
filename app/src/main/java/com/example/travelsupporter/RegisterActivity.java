package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelsupporter.API.RegisterRequest;
import com.example.travelsupporter.API.Travel_Supporter_Client;

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
        final EditText address = findViewById(R.id.address);
        final EditText dob = findViewById(R.id.dod);
        final EditText gender = findViewById(R.id.gender);
        Button okRegisterInBtn = findViewById(R.id.okRegisterInBtn);
        Button backRegisterInBtn = findViewById(R.id.backRegisterInBtn);
        okRegisterInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterRequest registerRequest = new RegisterRequest(password.getText().toString(),full_name.getText().toString(),
                        email.getText().toString(),phone.getText().toString(),address.getText().toString(),dob.getText().toString(),gender.getText().toString());

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
}
