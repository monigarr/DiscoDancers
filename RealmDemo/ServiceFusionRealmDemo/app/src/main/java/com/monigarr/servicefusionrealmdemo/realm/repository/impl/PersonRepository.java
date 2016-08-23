package com.monigarr.servicefusionrealmdemo.realm.repository.impl;

import com.monigarr.servicefusionrealmdemo.app.RealmDemoApp;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.realm.repository.IPersonRepository;
import com.monigarr.servicefusionrealmdemo.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class PersonRepository implements IPersonRepository {

    @Override
    public void addPerson(Person person, OnSavePersonCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        Person realmPerson = realm.createObject(Person.class);
        realmPerson.setId(UUID.randomUUID().toString());
        realmPerson.setFirstName(person.getFirstName());
        realmPerson.setLastName(person.getLastName());
        realmPerson.setDob(person.getDob());
        realmPerson.setZipcode(person.getZipcode());
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deletePersonById(String id, OnDeletePersonCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        Person result = realm.where(Person.class).equalTo(RealmTable.ID, id).findFirst();
        result.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllPeople(OnGetAllPeopleCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        RealmResults<Person> results = realm.where(Person.class).findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getPersonById(String id, OnGetPersonByIdCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        Person person = realm.where(Person.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(person);
    }
}
