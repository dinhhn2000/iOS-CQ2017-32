package com.ygaps.travelapp.Custom_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.utils.Member;

import java.util.ArrayList;

public class MemberAdapter extends ArrayAdapter<Member> {
    private Context context;
    private int resource;
    private ArrayList<Member> arrMember;

    public MemberAdapter(Context context, int resource, ArrayList<Member> arrMember) {
        super(context, resource, arrMember);
        this.context = context;
        this.resource = resource;
        this.arrMember = arrMember;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemberAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.member_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.memberName);
            viewHolder.phone = convertView.findViewById(R.id.memberPhone);
            viewHolder.create = convertView.findViewById(R.id.memberCreate);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MemberAdapter.ViewHolder) convertView.getTag();
        }
        final Member member = arrMember.get(position);
        viewHolder.name.setText(member.getName());
        viewHolder.phone.setText(member.getPhone());
        viewHolder.create.setText(member.isHost() ? "Creator" : "Member");

        return convertView;
    }

    public class ViewHolder {
        TextView phone;
        TextView create;
        TextView name;
    }

}
