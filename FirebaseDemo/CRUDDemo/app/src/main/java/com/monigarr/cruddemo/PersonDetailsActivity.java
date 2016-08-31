/*
 * Copyright (c) 2016.
 */

package com.monigarr.cruddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by monicapeters on 8/31/16.
 */

public class PersonDetailsActivity extends AppCompatActivity {
    private TextView Firstname,Lastname,Zipcode,Dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        Firstname = (TextView) findViewById(R.id.Firstname);
        Lastname = (TextView) findViewById(R.id.Lastname);
        Zipcode = (TextView) findViewById(R.id.Zipcode);
        Dob = (TextView) findViewById(R.id.Dob);

        int position = getIntent().getIntExtra("Position", -1);
        searchPerson(position);
    }

    public void searchPerson(int position) {
        String clickedKey = MainActivity.getInstance().getKeysArray().get(position);
        MainActivity.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Person personDetailsModel = dataSnapshot.getValue(Person.class);
                        Firstname.setText(getString(R.string.person_firstname,personDetailsModel.getFirstname()));
                        Lastname.setText(getString(R.string.person_lastname,personDetailsModel.getLastname()));
                        Zipcode.setText(getString(R.string.person_zipcode,personDetailsModel.getZipcode()));
                        Dob.setText(getString(R.string.person_dob, String.valueOf(personDetailsModel.getDob())));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}