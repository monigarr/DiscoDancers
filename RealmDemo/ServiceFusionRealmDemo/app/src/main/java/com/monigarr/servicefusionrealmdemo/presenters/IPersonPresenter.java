package com.monigarr.servicefusionrealmdemo.presenters;

import com.monigarr.servicefusionrealmdemo.model.Person;

/**
 * Created by monigarr on 8/22/16.
 */

public class IPersonPresenter extends IBasePresenter {

    void addPerson(Person person);
    void deletePersonById(int personId);
    void getAllPeople();
    void getPeopleById(String id);

}
