package com.example.travelsupporter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.travelsupporter.API.CreateTourRequest;
import com.example.travelsupporter.API.CreateTourResponse;
import com.example.travelsupporter.API.Travel_Supporter_Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateTourActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // private TextView textViewResult;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText tour_name = findViewById(R.id.tourName);
        final EditText minCost = findViewById(R.id.minCost);
        final EditText maxCost = findViewById(R.id.maxCost);
        final EditText start_date = findViewById(R.id.startDate);
        final EditText end_date = findViewById(R.id.endDate);
        final RadioButton is_private = findViewById(R.id.isPrivate);
        final EditText adults = findViewById(R.id.adults);
        final EditText children = findViewById(R.id.children);

        final ImageView avatar = findViewById(R.id.avatar);
        Button buttonLoadPicture = findViewById(R.id.buttonLoadPicture);
        Button okCreateTourInBtn = findViewById(R.id.okCreateTourInBtn);

        // Handle pick date
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), CreateTourActivity.this, 2019, 11, 18);

        ImageButton startDateBtn = findViewById(R.id.startDateBtn);
        ImageButton endDateBtn = findViewById(R.id.endDateBtn);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        buttonLoadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        okCreateTourInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Btn clicked", Toast.LENGTH_SHORT).show();

//                Call<CreateTourResponse> call = client.createTour(createTourRequest);
//                call.enqueue(new Callback<CreateTourResponse>() {
//                    @Override
//                    public void onResponse(Call<CreateTourResponse> call, Response<CreateTourResponse> response) {
//                        if (!response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        CreateTourResponse data = response.body();
//
//                        if (data != null && data.getStartDate() != 0 && data.getEndDate() != 0) {
//                            Toast.makeText(getApplicationContext(), "Create tout successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplication(), LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CreateTourResponse> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = findViewById(R.id.avatar);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
