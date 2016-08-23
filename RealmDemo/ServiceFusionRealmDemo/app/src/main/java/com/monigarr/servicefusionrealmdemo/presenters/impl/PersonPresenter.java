package com.monigarr.servicefusionrealmdemo.presenters.impl;

import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.presenters.IPersonPresenter;
import com.monigarr.servicefusionrealmdemo.realm.repository.IPersonRepository;
import com.monigarr.servicefusionrealmdemo.realm.repository.impl.PersonRepository;
import com.monigarr.servicefusionrealmdemo.view.activity.PeopleActivity;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class PersonPresenter implements IPersonPresenter {

    private PeopleActivity view;

    private IPersonRepository.OnDeletePersonCallback onDeletePersonCallback;
    private IPersonRepository.OnSavePersonCallback onSavePersonCallback;
    private IPersonRepository.OnGetAllPeopleCallback onGetAllPeopleCallback;
    private IPersonRepository.OnGetPersonByIdCallback onGetPersonByIdCallback;
    private IPersonRepository.OnGetPeopleCallback onGetPeopleCallback;

    private IPersonRepository personRepository;

    public PersonPresenter(PeopleActivity view) {
        this.view = view;
        personRepository = new PersonRepository();
    }

    @Override
    public void addPerson(Person person) {
        personRepository.addPerson(person, onSavePersonCallback);
    }

    @Override
    public void deletePersonById(String personId) {
        personRepository.deletePersonById(personId, onDeletePersonCallback);
    }

    @Override
    public void getAllPeople() {
        personRepository.getAllPeople(onGetAllPeopleCallback);
    }

    @Override
    public void getPersonById(String id) {
        personRepository.getPersonById(id, onGetPersonByIdCallback);
    }

    @Override
    public void subscribeCallbacks() {
        onSavePersonCallback = new IPersonRepository.OnSavePersonCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onDeletePersonCallback = new IPersonRepository.OnDeletePersonCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetAllPeopleCallback = new IPersonRepository.OnGetAllStudentsCallback() {
            @Override
            public void onSuccess(RealmResults<Person> people) {

            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetPersonByIdCallback = new IPersonRepository.OnGetPersonByIdCallback() {
            @Override
            public void onSuccess(Person person) {

            }

            @Override
            public void onError(String message) {

            }
        };
        onGetPeopleCallback = new IPersonRepository.OnGetPeopleCallback() {
            @Override
            public void onSuccess(RealmList<Person> people) {
                view.showPeople(people);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
    }

    @Override void unSubscribeCallbacks() {
        onDeletePersonCallback = null;
        onSavePersonCallback = null;
        onGetAllPeopleCallback = null;
        onGetPersonByIdCallback = null;
    }
}
