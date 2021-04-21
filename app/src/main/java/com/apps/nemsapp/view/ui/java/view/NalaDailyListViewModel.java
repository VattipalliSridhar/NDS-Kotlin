package com.apps.nemsapp.view.ui.java.view;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NalaDailyListViewModel extends ViewModel {
    private final NalaDailyListRepo nalaDailyListRepo;
    private final MutableLiveData<NalaDailyModel> liveData;
    private final MutableLiveData<String> message;

    public NalaDailyListViewModel() {
        nalaDailyListRepo = new NalaDailyListRepo();
        liveData = nalaDailyListRepo.getModelMutableLiveData();
        message = nalaDailyListRepo.getStringMutableLiveData();
    }


    public void getDailyData(String userid, String ulbid,
                             String wardid, String circleid, String apkversion,
                             NalaDailyListActivityJava mainActivity) {

        nalaDailyListRepo.getNalaDailyList(userid, ulbid, wardid, circleid, apkversion, mainActivity);

    }


    public MutableLiveData<NalaDailyModel> getDailyList() {
        return liveData;
    }

    public MutableLiveData<String> getMessageToShow() {
        return message;
    }
}
