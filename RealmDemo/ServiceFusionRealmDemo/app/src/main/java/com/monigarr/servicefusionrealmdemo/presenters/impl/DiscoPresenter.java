package com.monigarr.servicefusionrealmdemo.presenters.impl;

import com.monigarr.servicefusionrealmdemo.model.Disco;
import com.monigarr.servicefusionrealmdemo.presenters.IDiscoPresenter;
import com.monigarr.servicefusionrealmdemo.realm.repository.IDiscoRepository;
import com.monigarr.servicefusionrealmdemo.realm.repository.impl.DiscoRepository;
import com.monigarr.servicefusionrealmdemo.view.base.DiscoActivity;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class DiscoPresenter implements IDiscoPresenter {

    private DiscoActivity view;

    private IDiscoRepository repository;

    private IDiscoRepository.OnGetAllDiscoCallback getAllDiscoCallback;
    private IDiscoRepository.OnAddDiscoCallback addDiscoCallback;
    private IDiscoRepository.OnGetDiscoByIdCallback getSpecialDiscoCallback;
    private IDiscoRepository.OnDeleteDiscoCallback deleteDiscoCallback;

    public DiscoPresenter(DiscoActivity view) {
        this.view = view;
        repository = new DiscoRepository();
    }

    @Override
    public void getAllDiscos() {
        repository.getAllDiscos(getAllDiscoCallback);
    }

    @Override
    public void addDisco(String discoName) {
        Disco disco = new Disco(discoName);
        repository.addDisco(disco, addDiscoCallback);
    }

    @Override
    public void getDiscoById(String id) {
        repository.getDiscoById(id, getSpecialDiscoCallback);
    }

    @Override
    public void deleteDisco(int position) {
        repository.deleteDiscoByPosition(position, deleteDiscoCallback);
    }

    @Override
    public void deleteDiscoById(String Id) {
        repository.deleteDiscoById(Id, deleteDiscoCallback);
    }

    @Override
    public void subscribeCallbacks() {
        getAllDiscoCallback = new IDiscoRepository.OnGetAllDiscoCallback() {
            @Override
            public void onSuccess(RealmResults<Disco> discos) {
                view.showDiscos(discos);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        addDiscoCallback = new IDiscoRepository.OnAddDiscoCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        getSpecialDiscoCallback = new IDiscoRepository.OnGetDiscoByIdCallback() {
            @Override
            public void onSuccess(Disco disco) {

            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        deleteDiscoCallback = new IDiscoRepository.OnDeleteDiscoCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

    }

    @Override
    public void unSubscribeCallbacks() {
        getAllDiscoCallback = null;
        addDiscoCallback = null;
        getSpecialDiscoCallback = null;
        deleteDiscoCallback = null;
    }

}
