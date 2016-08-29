/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monicapeters on 8/27/16.
 */
@IgnoreExtraProperties
public class Friend {
    public String uid;
    public String firstname;
    public String lastname;
    public Date dob;
    public String zipcode;

    public Friend() {
        // Default constructor required for calls to DataSnapshot.getValue(Friend.class)
    }

    public Friend(String uid, String firstname, String lastname, String zipcode, Date dob) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.zipcode = zipcode;
        this.dob = dob;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("zipcode", zipcode);
        result.put("dob", dob);

        return result;
    }
}
