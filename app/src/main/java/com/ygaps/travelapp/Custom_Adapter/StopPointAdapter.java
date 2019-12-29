package com.ygaps.travelapp.Custom_Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.ygaps.travelapp.API.Requests.RatingStopPointRequest;
import com.ygaps.travelapp.API.Responses.MessageResponse;
import com.ygaps.travelapp.API.Travel_Supporter_Client;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.StopPointFeedbackListActivity;
import com.ygaps.travelapp.utils.StopPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StopPointAdapter extends ArrayAdapter<StopPoint> {
    private final Activity activity;
    private Context context;
    private int resource;
    private ArrayList<StopPoint> arrStopPoint;
    private PopupWindow mPopupWindow;

    public StopPointAdapter(Activity activity, int resource, ArrayList<StopPoint> arrStopPoint) {
        super(activity.getApplicationContext(), resource, arrStopPoint);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.resource = resource;
        this.arrStopPoint = arrStopPoint;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StopPointAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.stop_point_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = convertView.findViewById(R.id.stopPointAvatar);
            viewHolder.name = convertView.findViewById(R.id.stopPointName);
            viewHolder.location = convertView.findViewById(R.id.stopPointAddress);
            viewHolder.date = convertView.findViewById(R.id.stopPointDate);
            viewHolder.price = convertView.findViewById(R.id.stopPointPrice);
            viewHolder.service = convertView.findViewById(R.id.stopPointService);
            viewHolder.stopPointCommentsListBtn = convertView.findViewById(R.id.stopPointCommentsBtn);
            viewHolder.stopPointRatingBtn = convertView.findViewById(R.id.stopPointRatingBtn);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StopPointAdapter.ViewHolder) convertView.getTag();
        }
        final StopPoint stopPoint = arrStopPoint.get(position);
        viewHolder.avatar.setImageDrawable(context.getDrawable(R.mipmap.beach));
        viewHolder.name.setText(stopPoint.getName());

        ArrayList<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("Restaurant");
        serviceTypes.add("Hotel");
        serviceTypes.add("Spa");
        serviceTypes.add("Convenient Store");
        viewHolder.service.setText(serviceTypes.get(stopPoint.getServiceTypeId() - 1));

        ArrayList<String> provinces = new ArrayList<>();
        provinces.add("Hồ Chí Minh");
        provinces.add("Hà Nội");
        provinces.add("Nha Trang");
        provinces.add("Huế");
        provinces.add("Đà Nẵng");
        provinces.add("Quy Nhơn");
        provinces.add("Quảng Ngãi");
        provinces.add("Quảng Trị");
        viewHolder.location.setText(stopPoint.getAddress() + '-' + provinces.get(stopPoint.getProvinceId() - 1));

        String startDate;
        String endDate;
        if (stopPoint.getArrivalAt() > 0) {
            startDate = getDate(stopPoint.getArrivalAt());
        } else {
            startDate = getDate(Long.parseLong("0"));
        }
        if (stopPoint.getLeaveAt() > 0) {
            endDate = getDate(stopPoint.getLeaveAt());
        } else {
            endDate = getDate(Long.parseLong("0"));
        }
        viewHolder.date.setText(startDate + " - " + endDate);

        if (stopPoint.getMaxCost() == 0)
            viewHolder.price.setText(stopPoint.getMinCost() + " VND");
        else
            viewHolder.price.setText(stopPoint.getMinCost() + " - " + stopPoint.getMaxCost() + " VND");

        viewHolder.stopPointCommentsListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StopPointFeedbackListActivity.class);
                intent.putExtra("SERVICE_ID", (long) stopPoint.getServiceId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        viewHolder.stopPointRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float[] rating = {1};
                final RatingDialog ratingDialog = new RatingDialog.Builder(activity)
                        .threshold(10)
                        .playstoreUrl("")
                        .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                            @Override
                            public void onRatingSelected(float rate, boolean thresholdCleared) {
                                Toast.makeText(context, " " + rate, Toast.LENGTH_LONG).show();
                                rating[0] = rate;
                            }
                        })
                        .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                            @Override
                            public void onFormSubmitted(String feedback) {
                                Toast.makeText(context, feedback, Toast.LENGTH_LONG).show();
                                final SharedPreferences sharedPreferences = context.getSharedPreferences("authentication", Context.MODE_PRIVATE);

                                final Retrofit.Builder builder = new Retrofit.Builder()
                                        .baseUrl("http://35.197.153.192:3000/")
                                        .addConverterFactory(GsonConverterFactory.create());

                                Retrofit retrofit = builder.build();
                                final Travel_Supporter_Client client = retrofit.create(Travel_Supporter_Client.class);

                                // Get token
                                String token = sharedPreferences.getString("token", "");

                                RatingStopPointRequest request = new RatingStopPointRequest(stopPoint.getServiceId(),feedback,(int) rating[0]);

                                Call<MessageResponse> call = client.sendFeedbackToStopPoint(token, request);
                                final boolean[] result = {false};
                                call.enqueue(new Callback<MessageResponse>() {
                                    @Override
                                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                        if (!response.isSuccessful()) {
                                            Toast.makeText(context.getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        MessageResponse message = response.body();

                                        if (message != null && message.getMessage().equals("Successful")) {
                                            Toast.makeText(context.getApplicationContext(), "Rating successful", Toast.LENGTH_SHORT).show();
                                            result[0] = true;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                                    }
                                });
                            }
                        }).build();

                ratingDialog.show();
            }
        });

        return convertView;
    }

    public class ViewHolder {
        Button stopPointRatingBtn;
        Button stopPointCommentsListBtn;
        TextView name;
        TextView service;
        ImageView avatar;
        TextView location;
        TextView date;
        TextView price;

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

}
