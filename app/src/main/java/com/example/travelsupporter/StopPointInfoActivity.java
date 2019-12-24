package com.example.travelsupporter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
