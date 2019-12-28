package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ygaps.travelapp.API.CreateTourRequest;
import com.ygaps.travelapp.API.CreateTourResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateTourActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // private TextView textViewResult;

    private static int RESULT_LOAD_IMAGE = 0;
    private static int RESULT_START_LOCATION = 1;
    private static int RESULT_DEST_LOCATION = 2;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private String imageBase64 = "";

    // Input data
    private long startTime = -1;
    private long endTime = -1;
    private double startLat = -1;
    private double startLong = -1;
    private double endLat = -1;
    private double endLong = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://35.197.153.192:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

        final EditText tour_name = findViewById(R.id.tourName);
        final TextView start_date = findViewById(R.id.startDateTV);
        final TextView end_date = findViewById(R.id.endDateTV);
        final RadioButton is_private = findViewById(R.id.isPrivate);
        final EditText adults = findViewById(R.id.adultsEditText);
        final EditText children = findViewById(R.id.childrenEditText);
        final EditText maxCost = findViewById(R.id.maxCostEditText);
        final EditText minCost = findViewById(R.id.minCostEditText);
        Button buttonLoadPicture = findViewById(R.id.buttonLoadPicture);
        Button confirmCreateTourBtn = findViewById(R.id.confirmCreateTourBtn);
//        Button settingBtn = findViewById(R.id.settingBtn);

//        settingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplication(), SettingActivity.class);
//                startActivity(intent);
//            }
//        });
        // Handle edit text when empty
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adults.getText().length() == 0) {
                    adults.setText("0");
                }
                if (children.getText().length() == 0) {
                    children.setText("0");
                }
                if (minCost.getText().length() == 0) {
                    minCost.setText("0");
                }
                if (maxCost.getText().length() == 0) {
                    maxCost.setText("0");
                }
            }
        };
        adults.addTextChangedListener(watcher);
        children.addTextChangedListener(watcher);
        minCost.addTextChangedListener(watcher);
        maxCost.addTextChangedListener(watcher);

        // Handle pick date
        ImageButton startDateBtn = findViewById(R.id.startDateBtn);
        ImageButton endDateBtn = findViewById(R.id.endDateBtn);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTourActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTourActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "Date choosed: " + dayOfMonth + "/" + month + "/" + year,
                        Toast.LENGTH_SHORT).show();
                String dateInString = dayOfMonth + "/" + month + "/" + year;
                start_date.setText(dateInString);

                // Convert to timestamp
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(dateInString);
                    startTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "Date choosed: " + dayOfMonth + "/" + month + "/" + year,
                        Toast.LENGTH_SHORT).show();
                String dateInString = dayOfMonth + "/" + month + "/" + year;
                end_date.setText(dateInString);

                // Convert to timestamp
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(dateInString);
                    endTime = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        // HANDLE PIN LOCATION
        ImageButton startLocation = findViewById(R.id.startLocation);
        ImageButton destLocation = findViewById(R.id.destinationLocation);

        startLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), GetLocationActivity.class);
                startActivityForResult(intent, RESULT_START_LOCATION);
            }
        });

        destLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), GetLocationActivity.class);
                startActivityForResult(intent, RESULT_DEST_LOCATION);
            }
        });

        // HANDLE CHOOSE PICTURE
        buttonLoadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        confirmCreateTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if user fill all necessary fields
                if (tour_name.getText().toString() == "" || startTime == -1 || endTime == -1 ||
                        startLat == -1 || startLong == -1 || endLat == -1 || endLong == -1) {
                    Toast.makeText(getApplicationContext(), "Please fill the other fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                CreateTourRequest createTourRequest = new CreateTourRequest(
                        tour_name.getText().toString(),
                        startTime,
                        endTime,
                        startLat,
                        startLong,
                        endLat,
                        endLong,
                        is_private.isChecked(),
                        Integer.parseInt(adults.getText().toString()),
                        Integer.parseInt(children.getText().toString()),
                        Integer.parseInt(minCost.getText().toString()),
                        Integer.parseInt(maxCost.getText().toString())
//                        imageBase64
                );

                // Get token
                String token = sharedPreferences.getString("token", "");

                Call<CreateTourResponse> call = client.createTour(token, createTourRequest);
                call.enqueue(new Callback<CreateTourResponse>() {
                    @Override
                    public void onResponse(Call<CreateTourResponse> call, Response<CreateTourResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d("createTour", "onResponse: Create fail - " + response.message());
                            return;
                        }
                        CreateTourResponse data = response.body();

                        if (data != null && data.getStartDate() != 0 && data.getEndDate() != 0) {
                            Toast.makeText(getApplicationContext(), "Create tout successful", Toast.LENGTH_SHORT).show();
                            Log.d("createTour", "onResponse: Create success");
                            Intent intent = new Intent(getApplication(), AddStopPointActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateTourResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("createTour", "onResponse: Create fail - " + t.getMessage());
                    }
                });
            }
        });

    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri selectedImageUri = data.getData();
//            Toast.makeText(getApplicationContext(), "Image url: " + selectedImage, Toast.LENGTH_SHORT).show();
            ImageView imageView = findViewById(R.id.tourImage);
            imageView.setImageURI(Uri.parse(selectedImageUri.toString()));

            //encode image to base64 string
            if (selectedImageUri != null) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();

                //Use your Base64 String as you wish
                imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("Base64", imageBase64);
            }
        }

        if (requestCode == RESULT_START_LOCATION && resultCode == RESULT_OK && data != null) {
            TextView startLocationTV = findViewById(R.id.startLocationTV);
            startLocationTV.setMovementMethod(new ScrollingMovementMethod());
            startLong = data.getDoubleExtra("LONG", -1);
            startLat = data.getDoubleExtra("LAT", -1);
            String startLocale = String.format("%.3f", startLat) + '-' + String.format("%.3f", startLong);
            startLocationTV.setText(startLocale);
        }

        if (requestCode == RESULT_DEST_LOCATION && resultCode == RESULT_OK && data != null) {
            TextView destinationLocationTV = findViewById(R.id.destinationLocationTV);
            destinationLocationTV.setMovementMethod(new ScrollingMovementMethod());
            endLat = data.getDoubleExtra("LAT", -1);
            endLong = data.getDoubleExtra("LONG", -1);
            String endLocale = String.format("%.3f", endLat) + '-' + String.format("%.3f", endLong);
            destinationLocationTV.setText(endLocale);
        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
