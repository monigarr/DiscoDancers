/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.monigarr.MoniGarrChatDemo.R;
import com.monigarr.MoniGarrChatDemo.models.Friend;
import com.monigarr.MoniGarrChatDemo.viewholders.AddFriendActivity;
import com.monigarr.MoniGarrChatDemo.viewholders.UpdateFriendActivity;

/**
 * Created by monicapeters on 8/31/16.
 */

public class FriendDetailsAdapter extends BaseAdapter {
    private ArrayList<Friend> arrayListFriend;
    private Context context;
    private LayoutInflater inflater;

    public FriendDetailsAdapter(Context context, ArrayList<Friend> arrayListFriend) {
        this.context = context;
        this.arrayListFriend = arrayListFriend;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListFriend.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListFriend.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.list_friends, null);
            holder = new Holder();
            holder.firstname = (TextView) v.findViewById(R.id.Firstname);
            holder.lastname = (TextView) v.findViewById(R.id.Lastname);
            holder.zipcode = (TextView) v.findViewById(R.id.Zipcode);
            holder.dob = (TextView) v.findViewById(R.id.Dob);
            holder.EditFriend = (ImageView) v.findViewById(R.id.EditFriend);
            holder.DeleteFriend = (ImageView) v.findViewById(R.id.DeleteFriend);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.firstname.setText(arrayListFriend.get(position).getFirstname());
        holder.lastname.setText(arrayListFriend.get(position).getLastname());
        holder.zipcode.setText(arrayListFriend.get(position).getZipcode());
        holder.dob.setText(arrayListFriend.get(position).getDob());

        //Edit
        holder.EditFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateFriendActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Position", position);
                context.getApplicationContext().startActivity(intent);
            }
        });

        //Delete
        holder.DeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(context, position);
            }
        });
        return v;
    }

    class Holder {
        TextView firstname,lastname,zipcode,dob;
        ImageView DeleteFriend, EditFriend;
    }

    public static void ShowConfirmDialog(Context context, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("You really want to delete this friend?")
                .setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddFriendActivity.getInstance().deleteFriend(position);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}