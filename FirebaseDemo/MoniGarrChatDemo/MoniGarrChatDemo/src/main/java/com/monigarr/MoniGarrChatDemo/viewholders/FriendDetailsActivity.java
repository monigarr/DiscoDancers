/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.viewholders;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.monigarr.MoniGarrChatDemo.R;
import com.monigarr.MoniGarrChatDemo.models.Friend;


/**
 * Created by monicapeters on 8/31/16.
 */

public class FriendDetailsActivity extends AppCompatActivity {
    private TextView Firstname,Lastname,Zipcode,Dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        Firstname = (TextView) findViewById(R.id.Firstname);
        Lastname = (TextView) findViewById(R.id.Lastname);
        Zipcode = (TextView) findViewById(R.id.Zipcode);
        Dob = (TextView) findViewById(R.id.Dob);

        int position = getIntent().getIntExtra("Position", -1);
        searchFriend(position);
    }

    public void searchFriend(int position) {
        String clickedKey = AddFriendActivity.getInstance().getKeysArray().get(position);
        AddFriendActivity.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Friend friendDetailsModel = dataSnapshot.getValue(Friend.class);
                        Firstname.setText(getString(R.string.firstname,friendDetailsModel.getFirstname()));
                        Lastname.setText(getString(R.string.lastname,friendDetailsModel.getLastname()));
                        Zipcode.setText(getString(R.string.zipcode,friendDetailsModel.getZipcode()));
                        Dob.setText(getString(R.string.dob, String.valueOf(friendDetailsModel.getDob())));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}