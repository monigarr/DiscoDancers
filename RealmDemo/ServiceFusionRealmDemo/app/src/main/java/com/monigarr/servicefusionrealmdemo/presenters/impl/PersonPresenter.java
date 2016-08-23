package com.monigarr.servicefusionrealmdemo.presenters.impl;

import com.monigarr.servicefusionrealmdemo.model.Disco;
import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.presenters.IPersonPresenter;
import com.monigarr.servicefusionrealmdemo.realm.repository.IDiscoRepository;
import com.monigarr.servicefusionrealmdemo.realm.repository.IPersonRepository;
import com.monigarr.servicefusionrealmdemo.realm.repository.impl.DiscoRepository;
import com.monigarr.servicefusionrealmdemo.realm.repository.impl.PersonRepository;
import com.monigarr.servicefusionrealmdemo.view.base.PeopleActivity;

import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * Created by monigarr on 8/22/16.
 */

public class PersonPresenter implements IPersonPresenter {

    private PeopleActivity view;

    private IPersonRepository.OnDeletePersonCallback onDeletePersonCallback;
    private IPersonRepository.OnSavePersonCallback onSavePersonCallback;
    private IPersonRepository.OnGetAllPersonsCallback onGetAllPersonsCallback;
    private IPersonRepository.OnGetPersonByIdCallback onGetPersonByIdCallback;
    private IPersonRepository.OnGetPersonsByDiscoIdCallback onGetPersonsByDiscoIdCallback;
    private IDiscoRepository.OnGetDiscoByIdCallback onGetDiscoByIdCallback;

    private IPersonRepository personRepository;
    private IDiscoRepository discoRepository;

    public PersonPresenter(PeopleActivity view) {
        this.view = view;
        personRepository = new PersonRepository();
        discoRepository = new DiscoRepository();
    }

    @Override
    public void addPerson(Person person) {
        personRepository.addPerson(person, onSavePersonCallback);
    }

    @Override
    public void addPersonByDiscoId(Person person, String discoId) {
        personRepository.addPersonByDiscoId(person, discoId, onSavePersonCallback);
    }

    @Override
    public void deletePersonByPosition(int position) {
        personRepository.deletePersonByPosition(position, onDeletePersonCallback);
    }

    @Override
    public void deletePersonById(String personId) {
        personRepository.deletePersonById(personId, onDeletePersonCallback);
    }

    @Override
    public void getAllPersons() {
        personRepository.getAllPersons(onGetAllPersonsCallback);
    }

    @Override
    public void getPersonById(String id) {
        personRepository.getPersonById(id, onGetPersonByIdCallback);
    }

    @Override
    public void getPersonsByDiscoId(String id) {
        personRepository.getPersonsByDiscoId(id, onGetPersonsByDiscoIdCallback);
    }

    @Override
    public void getDiscoById(String id) {
        discoRepository.getDiscoById(id, onGetDiscoByIdCallback);
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
        onGetAllPersonsCallback = new IPersonRepository.OnGetAllPersonsCallback() {
            @Override
            public void onSuccess(RealmResults<Person> persons) {

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
    }

    @Override
    public void unSubscribeCallbacks() {
        onDeletePersonCallback = null;
        onSavePersonCallback = null;
        onGetAllPersonsCallback = null;
        onGetPersonsByDiscoIdCallback = null;
        onGetPersonByIdCallback = null;
    }
}
