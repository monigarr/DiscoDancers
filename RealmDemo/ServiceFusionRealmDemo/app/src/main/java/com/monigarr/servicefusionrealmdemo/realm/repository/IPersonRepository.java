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

    interface OnGetAllPeopleCallback {
        void onSuccess(RealmResults<Person> people);
        void onError(String message);
    }

    interface OnGetPeopleCallback {
        void onSuccess(RealmResults<Person> people);
        void onError(String message);
    }

    void addPerson(Person person, OnSavePersonCallback callback);
    void addPersonById(String id, OnDeletePersonCallback callback);
    void deletePersonById(String id, OnDeletePersonCallback);
    void getAllPeople(OnGetAllPeopleCallback callback);
}
