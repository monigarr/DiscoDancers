/*
 * Copyright (c) 2016.
 */

package com.monigarr.cruddemo;

/**
 * Created by monicapeters on 8/31/16.
 */

import java.util.HashMap;
import java.util.Map;

public class Person {

    private String firstname;
    private String lastname;
    private String zipcode;
    private int dob;

    public Person(){
        /*Blank default constructor essential for Firebase*/
    }

    public void setFirstname(String name) {
        this.firstname = firstname;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getLastname() {
        return lastname;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getZipcode() {
        return zipcode;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }
    public int getDob() {
        return dob;
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