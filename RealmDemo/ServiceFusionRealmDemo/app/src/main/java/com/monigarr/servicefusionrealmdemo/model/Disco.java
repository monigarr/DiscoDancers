package com.monigarr.servicefusionrealmdemo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by monigarr on 8/22/16.
 */

public class Disco extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private RealmList<Person> persons;

    public Disco() {
    }

    public Disco(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Person> getPersons() {
        return persons;
    }

    public void setPeople(RealmList<Person> persons) {
        this.persons = persons;
    }
}
