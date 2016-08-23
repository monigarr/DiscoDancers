package com.monigarr.servicefusionrealmdemo.presenters;

/**
 * Created by monigarr on 8/22/16.
 */

public interface IDiscoPresenter extends IBasePresenter {

    void addDisco(String discoName);
    void deleteDisco(int position);
    void deleteDiscoById(String id);
    void getDiscoById(String id);
    void getAllDiscos();
}
