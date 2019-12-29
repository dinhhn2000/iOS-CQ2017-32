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
import com.ygaps.travelapp.utils.StopPointFeedback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StopPointFeedbackAdapter extends ArrayAdapter<StopPointFeedback> {
    private Context context;
    private int resource;
    private ArrayList<StopPointFeedback> arrStopPointFeedback;

    public StopPointFeedbackAdapter(Context context, int resource, ArrayList<StopPointFeedback> arrStopPointFeedback) {
        super(context, resource, arrStopPointFeedback);
        this.context = context;
        this.resource = resource;
        this.arrStopPointFeedback = arrStopPointFeedback;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StopPointFeedbackAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.stop_point_feedback_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.feedbackUser);
            viewHolder.phone = convertView.findViewById(R.id.feedbackPhone);
            viewHolder.date = convertView.findViewById(R.id.feedbackDate);
            viewHolder.content = convertView.findViewById(R.id.feedbackContent);
            viewHolder.rating = convertView.findViewById(R.id.feedbackRating);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StopPointFeedbackAdapter.ViewHolder) convertView.getTag();
        }
        final StopPointFeedback feedback = arrStopPointFeedback.get(position);
        viewHolder.name.setText(feedback.getName());
        viewHolder.phone.setText(feedback.getPhone());
        viewHolder.content.setText(feedback.getFeedback());
        viewHolder.rating.setText(feedback.getPoint() + ".0");

        String date;
        if (feedback.getCreatedOn() > 0) {
            date = getDate(feedback.getCreatedOn());
        } else {
            date = getDate(0);
        }
        viewHolder.date.setText(date);

        return convertView;
    }

    public class ViewHolder {
        TextView rating;
        TextView content;
        TextView phone;
        TextView name;
        TextView date;

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

}
