package com.monigarr.servicefusionrealmdemo.realm.repository;

import com.monigarr.servicefusionrealmdemo.model.Person;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public interface IPersonRepository {

    interface OnSavePersonCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnDeletePersonCallback {
        void onSuccess();
        void onError(String message);
    }


    interface OnGetPersonByIdCallback {
        void onSuccess(Person person);
        void onError(String message);
    }

    interface OnGetAllPersonsCallback {
        void onSuccess(RealmResults<Person> persons);
        void onError(String message);
    }

    interface OnGetPersonsByDiscoIdCallback {
        void onSuccess(RealmResults<Person> persons);
        void onError(String message);
    }

    interface OnGetPeopleCallback {
        void onSuccess(RealmResults<Person> persons);
        void onError(String message);
    }

    void addPerson(Person person, OnSavePersonCallback callback);
    void addPersonByDiscoId(Person person, String discoId, OnSavePersonCallback callback);
    void deletePersonById(String id, OnDeletePersonCallback callback);
    void deletePersonByPosition(int position, OnDeletePersonCallback callback);
    void getAllPersons(OnGetAllPersonsCallback callback);
    void getPersonsByDiscoId(String id, OnGetPersonsByDiscoIdCallback callback);
    void getPersonById(String id, OnGetPersonByIdCallback callback);
}
