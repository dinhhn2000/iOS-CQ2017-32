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
import com.ygaps.travelapp.utils.Notification;
import com.ygaps.travelapp.utils.Tour;
import com.ygaps.travelapp.NotificationList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class notificationAdapter extends ArrayAdapter<Notification> {
    private Context context;
    private int resource;
    private ArrayList<Notification> arrNotifi;

    public notificationAdapter(Context context, int resource, ArrayList<Notification> arrNotifi) {
        super(context, resource, arrNotifi);
        this.context = context;
        this.resource = resource;
        this.arrNotifi = arrNotifi;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        notificationAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_notification, parent, false);
            viewHolder = new notificationAdapter.ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatarNotification);
            viewHolder.notification = (TextView) convertView.findViewById(R.id.notificationTV);
            viewHolder.time = (TextView) convertView.findViewById(R.id.timeNotification);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (notificationAdapter.ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    public class ViewHolder {
        ImageView avatar;
        TextView notification;
        TextView time;

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

}
