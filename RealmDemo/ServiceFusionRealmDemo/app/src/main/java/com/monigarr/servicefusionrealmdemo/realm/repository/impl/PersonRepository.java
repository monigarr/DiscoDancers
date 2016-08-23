package com.monigarr.servicefusionrealmdemo.realm.repository.impl;

import com.monigarr.servicefusionrealmdemo.app.RealmDemoApp;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.model.Disco;
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
        realmPerson.setName(person.getName());
        realmPerson.setLastName(person.getLastName());
        realmPerson.setDob(person.getDob());
        realmPerson.setZipcode(person.getZipcode());
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void addPersonByDiscoId(Person person, String discoId, OnSavePersonCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();

        Person realmPerson = realm.createObject(Person.class);
        realmPerson.setId(UUID.randomUUID().toString());
        realmPerson.setName(person.getName());
        realmPerson.setLastName(person.getLastName());
        realmPerson.setDob(person.getDob());
        realmPerson.setZipcode(person.getZipcode());

        Disco disco = realm.where(Disco.class).equalTo(RealmTable.ID, discoId).findFirst();
        disco.getPersons().add(realmPerson);

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
    public void deletePersonByPosition(int position, OnDeletePersonCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        RealmQuery<Person> query = realm.where(Person.class);
        RealmResults<Person> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllPersons(OnGetAllPersonsCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        RealmResults<Person> results = realm.where(Person.class).findAll();

        if (callback != null)
            callback.onSuccess(results);
    }

    @Override
    public void getAllPersonsByDiscoId(String id, OnGetPersonsCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        Disco disco = realm.where(Disco.class).equalTo(RealmTable.ID, id).findFirst();
        RealmList<Person> persons = disco.getPersons();

        if (callback != null)
            callback.onSuccess(persons);

    }

    @Override
    public void getPersonById(String id, OnGetPersonByIdCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        Person person = realm.where(Person.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(person);
    }
}
