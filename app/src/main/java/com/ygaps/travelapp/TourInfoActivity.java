package com.ygaps.travelapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.text.format.DateFormat;
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

import com.ygaps.travelapp.API.Requests.UpdateTourRequest;
import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Responses.UpdateTourResponse;
import com.ygaps.travelapp.API.Responses.getTourInfoResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TourInfoActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 0;
    private static int RESULT_START_LOCATION = 1;
    private static int RESULT_DEST_LOCATION = 2;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private String imageBase64 = "";
    private long temp;
    // Input data
    private long startTime = -1;
    private long endTime = -1;
    private double startLat = -1;
    private double startLong = -1;
    private double endLat = -1;
    private double endLong = -1;
    private long tourId;

    private int status;
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info);
        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

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
        final Button updateInfoTour = findViewById(R.id.ConfirmTourInfo);
        final Button finish_trip = findViewById(R.id.finishTrip);
        Intent intent = this.getIntent();
        Bundle db = getIntent().getExtras();

            tourId = intent.getLongExtra("TOUR_ID",0);

        Log.d("TourInfoResponse", "TourID:  " + tourId);
        String token = sharedPreferences.getString("token","");

        Call<getTourInfoResponse> call = client.getTour(token,tourId);

        call.enqueue(new Callback<getTourInfoResponse>() {
            @Override
            public void onResponse(Call<getTourInfoResponse> call, Response<getTourInfoResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("TourInfoResponse", "onResponse: Response fail - " + response.message());
                    return;
                }
                status = response.body().getStatus();
                String name = response.body().getName();
                long Mincost = response.body().getMinCost();
                long Maxcost = response.body().getMaxCost();
                long Adult = response.body().getadults();
                long Childs = response.body().getChilds();
                long startDate = response.body().getStartDate();
                long endDate = response.body().getEndDate();
                boolean IsPrivate = response.body().isPrivate();
                getTourInfoResponse data = response.body();
                String Start = getDate(startDate);
                String End = getDate(endDate);

                tour_name.setText(name);
                minCost.setText(Long.toString(Mincost));
                maxCost.setText(Long.toString(Maxcost));
                adults.setText(Long.toString(Adult));
                children.setText(Long.toString(Childs));
                start_date.setText(Start);
                end_date.setText(End);

                if (IsPrivate = true)
                {
                    is_private.setChecked(true);
                } else is_private.setChecked(false);

                Log.d("TourInfoResponse", "response start date + end date " + startDate + " " + endDate);
                Log.d("TourInfoResponse", data.toString());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                if(data == null)
                {
                    Log.d("TourInfoResponse", "response fail " + response.message());
                    return;
                }


            }

            @Override
            public void onFailure(Call<getTourInfoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("UserInfoResponse", "onResponse: UserInfoResponse fail - " + t.getMessage());
            }

        });



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
                        TourInfoActivity.this,
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
                        TourInfoActivity.this,
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
        updateInfoTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tour_name.getText().toString() == "" || maxCost.getText().toString() == "" || minCost.getText().toString() == "" ||
                        adults.getText().toString() == "" || children.getText().toString() == ""|| start_date.getText().toString() == "" || end_date.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Please fill the other fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String avatar = "";
                String id = Long.toString(tourId);
                UpdateTourRequest updateTourRequest = new UpdateTourRequest( id ,
                        tour_name.getText().toString(),
                        startTime,
                        endTime,
                        Integer.parseInt(adults.getText().toString()),
                        Integer.parseInt(children.getText().toString()),
                        Integer.parseInt(minCost.getText().toString()),
                        Integer.parseInt(maxCost.getText().toString()),
                        is_private.isChecked(),
                        status);
                String token = sharedPreferences.getString("token", "");
                Log.d("Update Tour Info", " Update data " + tour_name + startTime + endTime + startLong + startLat + endLat + endLong);
                Call<UpdateTourResponse> call = client.updateTour(token, updateTourRequest);
                call.enqueue(new Callback<UpdateTourResponse>() {
                    @Override
                    public void onResponse(Call<UpdateTourResponse> call, Response<UpdateTourResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d("Update Tour", "onResponse: Create fail - " + response.message());
                            return;
                        }
                        UpdateTourResponse data = response.body();

                        if (data != null && data.getStartDate() != 0 && data.getEndDate() != 0) {
                            Toast.makeText(getApplicationContext(), "Create tout successful", Toast.LENGTH_SHORT).show();
                            Log.d("Update Tour", "onResponse: Create success");

                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateTourResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Update Tour", "onResponse: Update fail - " + t.getMessage());

                    }
                });
            }



        });
        finish_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = sharedPreferences.getString("token", "");
                String id = Long.toString(tourId);

                UpdateTourRequest finishTripRequest = new UpdateTourRequest( id,2);
                Call<UpdateTourResponse> call = client.updateTour(token, finishTripRequest);
                call.enqueue(new Callback<UpdateTourResponse>() {
                    @Override
                    public void onResponse(Call<UpdateTourResponse> call, Response<UpdateTourResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                            Log.d("Update Tour", "Finish Response fail - " + response.message());
                            return;
                        }
                        UpdateTourResponse data = response.body();

                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<UpdateTourResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Update Tour", "Finish Response fail - " + t.getMessage());

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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
