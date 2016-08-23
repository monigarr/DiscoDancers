package com.monigarr.servicefusionrealmdemo.realm.table;

/**
 * Created by monigarr on 8/22/16.
 */

public interface RealmTable {

    String ID = "id";

    interface Person {
        String FIRSTNAME = "firstName";
        String LASTNAME = "lastName";
        String DOB = "dob";
        String ZIPCODE = "zipcode";
    }
}
