package com.monigarr.servicefusionrealmdemo.realm.module;

/**
 * Created by monigarr on 8/22/16.
 */

import com.monigarr.servicefusionrealmdemo.model.Person;
import com.monigarr.servicefusionrealmdemo.model.Disco;
import io.realm.annotations.RealmModule;

@RealmModule(classes = {Person.class, Disco.class})
public class DemoRealmModule {

}
