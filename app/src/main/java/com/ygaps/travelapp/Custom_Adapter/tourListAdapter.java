package com.ygaps.travelapp.Custom_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.utils.Tour;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class tourListAdapter extends ArrayAdapter<Tour> {
    private Context context;
    private int resource;
    private ArrayList<Tour> arrTour;

    public tourListAdapter(Context context, int resource, ArrayList<Tour> arrTour) {
        super(context, resource, arrTour);
        this.context = context;
        this.resource = resource;
        this.arrTour = arrTour;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tour_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.tourAvatar);
            viewHolder.location = (TextView) convertView.findViewById(R.id.tourLocation);
            viewHolder.date = (TextView) convertView.findViewById(R.id.tourDate);
            viewHolder.people = (TextView) convertView.findViewById(R.id.tourPeople);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tourPrice);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Tour tour = arrTour.get(position);
        viewHolder.avatar.setImageDrawable(context.getDrawable(R.mipmap.beach));
        viewHolder.location.setText(tour.getName());

        String startDate;
        String endDate;
        if(tour.getStartDate() != null && tour.getStartDate().charAt(0) != '-'){
            startDate = getDate(Long.parseLong(tour.getStartDate()));
        }
        else{
            startDate = getDate(Long.parseLong("0"));
        }
        if(tour.getEndDate() != null && tour.getEndDate().charAt(0) != '-'){
            endDate = getDate(Long.parseLong(tour.getEndDate()));
        }
        else{
            endDate = getDate(Long.parseLong("0"));
        }
        viewHolder.date.setText(startDate + " - " + endDate);

        int adult = tour.getAdult();
        int children = tour.getChild();
        String people = "";
        people += adult + " adults";
        people += "  - ";
        people += children + " children";
        viewHolder.people.setText(people);

        if (tour.getMaxCost().equalsIgnoreCase("0"))
            viewHolder.price.setText(tour.getMinCost() + " VND");
        else
            viewHolder.price.setText(tour.getMinCost() + " - " + tour.getMaxCost() + " VND");

        return convertView;
    }

    public class ViewHolder {
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
