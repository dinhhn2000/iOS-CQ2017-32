package com.ygaps.travelapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.utils.StopPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StopPointInfoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener arriveDateSetListener;
    private DatePickerDialog.OnDateSetListener leaveDateSetListener;

    private long arriveDateData = -1;
    private long arriveTimeData = -1;
    private long leaveDateData = -1;
    private long leaveTimeData = -1;
    private int mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_point_info);

        final SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);

        final EditText stopPointName = findViewById(R.id.stopPointName);
        final EditText stopPointAddress = findViewById(R.id.stopPointAddress);
        final EditText stopPointMaxCost = findViewById(R.id.maxCostEditText);
        final EditText stopPointMinCost = findViewById(R.id.minCostEditText);
        final TextView arriveDateTV = findViewById(R.id.arriveDateTV);
        final TextView arriveTimeTV = findViewById(R.id.arriveTimeTV);
        final TextView leaveDateTV = findViewById(R.id.leaveDateTV);
        final TextView leaveTimeTV = findViewById(R.id.leaveTimeTV);
        final long[] arriveAt = {0};
        final long[] leaveAt = {0};

        // Handle pick time & date
        ImageButton arriveDate = findViewById(R.id.arriveDateBtn);
        ImageButton arriveTime = findViewById(R.id.arriveTimeBtn);
        ImageButton leaveDate = findViewById(R.id.leaveDateBtn);
        ImageButton leaveTime = findViewById(R.id.leaveTimeBtn);

        arriveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StopPointInfoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        arriveDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        arriveDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "Date choosed: " + dayOfMonth + "/" + month + "/" + year,
                        Toast.LENGTH_SHORT).show();
                String dateInString = dayOfMonth + "/" + month + "/" + year;
                TextView arriveDate = findViewById(R.id.arriveDateTV);
                arriveDate.setText(dateInString);

                // Convert to timestamp
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(dateInString);
                    arriveDateData = date.getTime();
                    arriveAt[0] += arriveDateData;
                    Log.d("arriveDate", "onDateSet: " + arriveDateData);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        leaveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        StopPointInfoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        leaveDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        leaveDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), "Date choosed: " + dayOfMonth + "/" + month + "/" + year,
                        Toast.LENGTH_SHORT).show();
                String dateInString = dayOfMonth + "/" + month + "/" + year;
                TextView leaveDate = findViewById(R.id.leaveDateTV);
                leaveDate.setText(dateInString);

                // Convert to timestamp
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = formatter.parse(dateInString);
                    leaveDateData = date.getTime();
                    leaveAt[0] += leaveDateData;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        arriveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(StopPointInfoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                TextView arriveTimeTV = findViewById(R.id.arriveTimeTV);
                                arriveTimeTV.setText(hourOfDay + ":" + minute);
                                arriveAt[0] += (hourOfDay * 3600 + minute * 60) * 1000;
                                Log.d("arriveTime", "onDateSet: " + (hourOfDay * 3600 + minute * 60) * 1000);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        leaveTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(StopPointInfoActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                TextView leaveTimeTV = findViewById(R.id.leaveTimeTV);
                                leaveTimeTV.setText(hourOfDay + ":" + minute);
                                leaveAt[0] += (hourOfDay * 3600 + minute * 60) * 1000;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        // Handle service type
        final Spinner serviceTypeSpinner = findViewById(R.id.stopPointService);
        final int[] serviceTypeId = {0};

        List<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("Restaurant");
        serviceTypes.add("Hotel");
        serviceTypes.add("Spa");
        serviceTypes.add("Convenient Store");

        ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, serviceTypes);
        serviceTypeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        serviceTypeSpinner.setAdapter(serviceTypeAdapter);
        serviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(StopPointInfoActivity.this, serviceTypeSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                serviceTypeId[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Handle province
        final Spinner provinceSpinner = findViewById(R.id.stopPointProvince);
        final int[] provinceId = {0};

        List<String> provinces = new ArrayList<>();
        provinces.add("Hồ Chí Minh");
        provinces.add("Hà Nội");
        provinces.add("Nha Trang");
        provinces.add("Huế");
        provinces.add("Đà Nẵng");
        provinces.add("Quy Nhơn");
        provinces.add("Quảng Ngãi");
        provinces.add("Quảng Trị");

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(StopPointInfoActivity.this, provinceSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                provinceId[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Handle save stop point
        Button saveBtn = findViewById(R.id.saveStopPointBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user fill all necessary fields
                if (stopPointName.getText().toString() == "" || stopPointAddress.getText().toString() == "" ||
                        stopPointMaxCost.getText().toString() == "" || stopPointMinCost.getText().toString() == "" ||
                        arriveDateTV.getText().toString() == "" || arriveTimeTV.getText().toString() == "" ||
                        leaveDateTV.getText().toString() == "" || leaveTimeTV.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Please fill the other fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double lat = getIntent().getDoubleExtra("LAT", 0);
                double lon = getIntent().getDoubleExtra("LONG", 0);
                boolean isUpdate = getIntent().getBooleanExtra("IS_UPDATE", false);

                StopPoint newStopPoint;
                if (isUpdate) {
                    int id = getIntent().getIntExtra("STOP_POINT_ID", 0);
                    newStopPoint = new StopPoint(id, stopPointName.getText().toString(),
                            stopPointAddress.getText().toString(), provinceId[0] + 1,
                            lat, lon, arriveAt[0], leaveAt[0], serviceTypeId[0] + 1,
                            Integer.parseInt(stopPointMinCost.getText().toString()),
                            Integer.parseInt(stopPointMaxCost.getText().toString()));
                } else
                    newStopPoint = new StopPoint(stopPointName.getText().toString(),
                            stopPointAddress.getText().toString(), provinceId[0] + 1,
                            lat, lon, arriveAt[0], leaveAt[0], serviceTypeId[0] + 1,
                            Integer.parseInt(stopPointMinCost.getText().toString()),
                            Integer.parseInt(stopPointMaxCost.getText().toString()));

                // Move back to create tour screen
                Intent intent = new Intent();
                intent.putExtra("NEW_STOP_POINT", newStopPoint);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
