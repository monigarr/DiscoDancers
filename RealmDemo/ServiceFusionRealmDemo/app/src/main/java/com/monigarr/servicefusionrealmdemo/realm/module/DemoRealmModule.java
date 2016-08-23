package com.monigarr.servicefusionrealmdemo.realm.module;

/**
 * Created by monigarr on 8/22/16.
 */

import com.monigarr.servicefusionrealmdemo.model.Person;

import io.realm.annotations.RealmModule;

@RealmModule(classes = {Person.class})

public class DemoRealmModule {

}
