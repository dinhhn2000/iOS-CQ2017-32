package com.ygaps.travelapp.Custom_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.StopPointListActivity;
import com.ygaps.travelapp.utils.StopPoint;
import com.ygaps.travelapp.utils.Tour;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class stopPointAdapter extends ArrayAdapter<StopPoint> {
    private Context context;
    private int resource;
    private ArrayList<StopPoint> arrStopPoint;

    public stopPointAdapter(Context context, int resource, ArrayList<StopPoint> arrStopPoint) {
        super(context, resource, arrStopPoint);
        this.context = context;
        this.resource = resource;
        this.arrStopPoint = arrStopPoint;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        stopPointAdapter.ViewHolder viewHolder;
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
            viewHolder = (stopPointAdapter.ViewHolder) convertView.getTag();
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
        if (stopPoint.getArriveAt() > 0) {
            startDate = getDate(stopPoint.getArriveAt());
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
                Intent intent = new Intent(context, StopPointListActivity.class);
                intent.putExtra("TOUR_ID", stopPoint.getId());
                context.startActivity(intent);
            }
        });

        viewHolder.stopPointRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    public class ViewHolder {
        Button stopPointRatingBtn;
        Button stopPointCommentsListBtn;
        TextView name;
        TextView service;
        Button stopPointListBtn;
        ImageView avatar;
        TextView location;
        TextView date;
        TextView people;
        TextView price;

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }
}
