package com.monigarr.servicefusionrealmdemo.realm.repository.impl;

import com.monigarr.servicefusionrealmdemo.app.RealmDemoApp;
import com.monigarr.servicefusionrealmdemo.model.Disco;
import com.monigarr.servicefusionrealmdemo.realm.repository.IDiscoRepository;
import com.monigarr.servicefusionrealmdemo.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class DiscoRepository implements IDiscoRepository {

    @Override
    public void addDisco(Disco disco, OnAddDiscoCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        Disco d = realm.createObject(Disco.class);
        d.setId(UUID.randomUUID().toString());
        d.setName(disco.getName());
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteDiscoById(String Id, OnDeleteDiscoCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        Disco disco = realm.where(Disco.class).equalTo(RealmTable.ID, Id).findFirst();
        disco.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteDiscoByPosition(int position, OnDeleteDiscoCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        realm.beginTransaction();
        RealmQuery<Disco> query = realm.where(Disco.class);
        RealmResults<Disco> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getDiscoById(String id, OnGetDiscoByIdCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        Disco result = realm.where(Disco.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(result);
    }

    @Override
    public void getAllDiscos(OnGetAllDiscoCallback callback) {
        Realm realm = Realm.getInstance(RealmDemoApp.getInstance());
        RealmQuery<Disco> query = realm.where(Disco.class);
        RealmResults<Disco> results = query.findAll();

        if (callback != null)
            callback.onSuccess(results);
    }
}
