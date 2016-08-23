package com.monigarr.servicefusionrealmdemo.presenters;

import com.monigarr.servicefusionrealmdemo.model.Person;

/**
 * Created by monigarr on 8/22/16.
 */

public interface IPersonPresenter extends IBasePresenter {

    void addPerson(Person person);
    void addPersonByDiscoId(Person person, String discoId);
    void deletePersonByPosition(int position);
    void deletePersonById(String personId);
    void getAllPersons();
    void getAllPersonsByDiscoId(String id);
    void getPersonById(String id);
    void getDiscoById(String id);
}
