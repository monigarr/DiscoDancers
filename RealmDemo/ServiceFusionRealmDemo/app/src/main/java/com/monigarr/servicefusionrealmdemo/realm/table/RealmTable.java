package com.monigarr.servicefusionrealmdemo.realm.table;

/**
 * Created by monigarr on 8/22/16.
 */

public interface RealmTable {

    String ID = "id";

    interface Disco {
        String PERSONS = "persons";
        String NAME = "name";
    }

    interface Person {
        String NAME = "firstname";
        String LASTNAME = "lastname";
        String DOB = "dob";
        String ZIPCODE = "zipcode";
    }
}
