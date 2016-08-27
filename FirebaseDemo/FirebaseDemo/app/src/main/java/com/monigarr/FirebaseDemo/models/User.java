/*
 * Copyright (c) 2016.
 */

package com.monigarr.FirebaseDemo.models;

import com.google.firebase.database.IgnoreExtraProperties;


/**
 * Created by monicapeters on 8/27/16.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    //public String dob;
    public String zipcode;

    public User(String username, String email, String zipcode) {
        this.username = username;
        this.email = email;
        this.zipcode = zipcode;
        //this.dob = dob;
    }
}

