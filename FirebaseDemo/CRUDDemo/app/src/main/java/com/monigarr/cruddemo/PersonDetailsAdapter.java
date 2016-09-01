/*
 * Copyright (c) 2016.
 */

package com.monigarr.cruddemo;

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
/**
 * Created by monicapeters on 8/31/16.
 */

public class PersonDetailsAdapter extends BaseAdapter {
    private ArrayList<Person> arrayListPerson;
    private Context context;
    private LayoutInflater inflater;

    public PersonDetailsAdapter(Context context, ArrayList<Person> arrayListPerson) {
        this.context = context;
        this.arrayListPerson = arrayListPerson;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayListPerson.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListPerson.get(position);
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
            v = inflater.inflate(R.layout.list_person, null);
            holder = new Holder();
            holder.Firstname = (TextView) v.findViewById(R.id.Firstname);
            holder.Lastname = (TextView) v.findViewById(R.id.Lastname);
            holder.Dob = (TextView) v.findViewById(R.id.Dob);
            holder.EditPerson = (ImageView) v.findViewById(R.id.EditPerson);
            holder.DeletePerson = (ImageView) v.findViewById(R.id.DeletePerson);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.Firstname.setText(arrayListPerson.get(position).getFirstname());
        holder.Lastname.setText(arrayListPerson.get(position).getLastname());
        //holder.Dob.setText(arrayListPerson.get(position).getDob());
        holder.EditPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddOrUpdatePersonActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Position", position);
                context.getApplicationContext().startActivity(intent);
            }
        });
        holder.DeletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowConfirmDialog(context, position);
            }
        });
        return v;
    }

    class Holder {
        TextView Firstname,Lastname,Dob;
        ImageView DeletePerson,EditPerson;
    }

    public static void ShowConfirmDialog(Context context, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this entry?")
                .setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.getInstance().deletePerson(position);
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