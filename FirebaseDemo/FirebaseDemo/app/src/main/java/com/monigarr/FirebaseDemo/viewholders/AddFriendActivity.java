/*
 * Copyright (c) 2016.
 */

package com.monigarr.FirebaseDemo.viewholders;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monigarr.FirebaseDemo.R;
import com.monigarr.FirebaseDemo.models.Friend;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monicapeters on 8/27/16.
 */

public class AddFriendActivity {

    private DatabaseReference mDatabase;

    private FloatingActionButton fbAdd;
    private Button addFriend;
    private Button viewFriends;
    private Button updateFriend;
    private Button deleteFriend;
    private RecyclerView rvFriends;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate();
        setContentView(R.layout.activity_add_friend);

    }

    mDatabase = FirebaseDatabase.getInstance().getReference();

    //Create Friend
    private void addNewFriend(String id, String firstname, String lastname, String zipcode, Date dob) {

        //simultaneously create new friend at /friend-friends/$id/$friendid
        //and /friends/$id
        String key = mDatabase.child("friends").push().getKey();
        Friend friend = new Friend(id, firstname, lastname, zipcode,dob);
        Map<String, Object> friendValues = friend.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/friends/" + key, friendValues);
        childUpdates.put("/friend-friends" + id +"/" + key, friendValues);

        mDatabase.updateChildren(childUpdates);

    }

    //Read Friends
    private void viewFriends() {

    }

    //Update Friend
    private void updateFriend() {

    }

    //Delete Friend
    private void removeFriend() {

    }

}
