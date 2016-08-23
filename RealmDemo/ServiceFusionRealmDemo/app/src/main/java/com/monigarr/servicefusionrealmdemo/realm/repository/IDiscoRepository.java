package com.monigarr.servicefusionrealmdemo.realm.repository;

import com.monigarr.servicefusionrealmdemo.model.Disco;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public interface IDiscoRepository extends IBaseRepository {

    interface OnAddDiscoCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllDiscoCallback {
        void onSuccess(RealmResults<Disco> discos);
        void onError(String message);
    }
    interface OnGetDiscoByIdCallback {
        void onSuccess(Disco disco);
        void onError(String message);
    }

    interface OnDeleteDiscoCallback {
        void onSuccess();
        void onError(String message);
    }

    void addDisco(Disco disco, OnAddDiscoCallback callback);
    void deleteDiscoById(String Id, OnDeleteDiscoCallback callback);
    void deleteDiscoByPosition(int position, OnDeleteDiscoCallback callback);
    void getAllDiscos(OnGetAllDiscoCallback callback);
    void getDiscoById(String id, OnGetDiscoByIdCallback callback);
}