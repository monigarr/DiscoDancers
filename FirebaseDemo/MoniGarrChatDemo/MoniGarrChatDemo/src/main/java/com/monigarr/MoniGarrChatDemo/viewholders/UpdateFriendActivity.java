/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.viewholders;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

public class UpdateFriendActivity extends AppCompatActivity {

    Button bOK,bCancel;
    Friend friend;
    int position;
    EditText Firstname,Lastname,Zipcode,Dob;
    CoordinatorLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_friend);

        position = getIntent().getIntExtra("Position", -1);

        cl = (CoordinatorLayout) findViewById(R.id.cdlayout);

        Firstname = (EditText) findViewById(R.id.Firstname);
        Lastname = (EditText) findViewById(R.id.Lastname);
        Zipcode = (EditText) findViewById(R.id.Zipcode);
        Dob = (EditText) findViewById(R.id.Dob);

        bOK = (Button) findViewById(R.id.bOk);
        bCancel = (Button) findViewById(R.id.bCancel);

        if(position != -1) {
            getSupportActionBar().setTitle("Edit");
            searchFriend(position);
            friend = new Friend();
        }
        else {
            getSupportActionBar().setTitle("Add");
            friend = null;
        }

        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Firstname.getText().toString().trim().equals("") ||
                        Lastname.getText().toString().trim().equals("") ||
                        Zipcode.getText().toString().trim().equals("") ||
                        Dob.getText().toString().trim().equals("")) {
                    final Snackbar snackBar = Snackbar.make(cl, "Enter All Text.", Snackbar.LENGTH_LONG);
                    snackBar.setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    });
                    snackBar.show();
                } else {
                    Friend f = new Friend();
                    f.setFirstname(Firstname.getText().toString());
                    f.setLastname(Lastname.getText().toString());
                    f.setZipcode(Zipcode.getText().toString());
                    f.setDob(Dob.getText().toString());
                    if (friend == null)
                        AddFriendActivity.getInstance().addFriend(f);
                    else
                        AddFriendActivity.getInstance().updateFriendDetails(f, position);
                    finish();
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void searchFriend(int position) {
        String clickedKey = AddFriendActivity.getInstance().getKeysArray().get(position);
        AddFriendActivity.getInstance().getmDatabase().child(clickedKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get friend value
                        Friend friend = dataSnapshot.getValue(Friend.class);
                        Firstname.setText(friend.getFirstname());
                        Lastname.setText(friend.getLastname());
                        Zipcode.setText(friend.getZipcode());
                        Dob.setText(String.valueOf(friend.getDob()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}