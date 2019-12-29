package com.ygaps.travelapp.Custom_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.utils.TourComment;

import java.util.ArrayList;

public class TourCommentAdapter extends ArrayAdapter<TourComment> {
    private Context context;
    private int resource;
    private ArrayList<TourComment> arrTourComment;

    public TourCommentAdapter(Context context, int resource, ArrayList<TourComment> arrTourComment) {
        super(context, resource, arrTourComment);
        this.context = context;
        this.resource = resource;
        this.arrTourComment = arrTourComment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TourCommentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tour_comment_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.commentUser);
            viewHolder.content = convertView.findViewById(R.id.commentContent);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TourCommentAdapter.ViewHolder) convertView.getTag();
        }
        final TourComment comment = arrTourComment.get(position);
       //Log.d("zzz", "getView: " + position);
        viewHolder.name.setText(comment.getName());
        viewHolder.content.setText(comment.getComment() == null ? "Data lost... We are sorry" : comment.getComment());

        return convertView;
    }

    public class ViewHolder {
        TextView content;
        TextView name;
    }

}
