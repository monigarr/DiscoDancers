package com.monigarr.servicefusionrealmdemo.app;

import android.app.Application;
import com.monigarr.servicefusionrealmdemo.realm.module.DemoRealmModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by monigarr on 8/22/16.
 */

public class RealmDemoApp extends Application {

    private static RealmDemoApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).setModules(new DemoRealmModule()).build();
        Realm.setDefaultConfiguration(config);
    }

    public static RealmDemoApp getInstance() {
        return instance;
    }
}
