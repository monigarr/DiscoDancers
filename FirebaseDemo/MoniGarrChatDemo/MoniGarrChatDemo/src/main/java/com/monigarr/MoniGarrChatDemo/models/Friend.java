/*
 * Copyright (c) 2016.
 */

package com.monigarr.MoniGarrChatDemo.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monicapeters on 8/27/16.
 */
@IgnoreExtraProperties
public class Friend {

    private String firstname;
    private String lastname;
    private Date dob;
    private String zipcode;

    public Friend() {
        // Default constructor required for calls to DataSnapshot.getValue(Friend.class)
    }


    public String getFirstname(String firstname) {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname(String lastname) {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getZipcode(String zipcode) {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Date getDob(Date dob) {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstname);
        result.put("lastname", lastname);
        result.put("zipcode", zipcode);
        result.put("dob", dob);

        return result;
    }


}
