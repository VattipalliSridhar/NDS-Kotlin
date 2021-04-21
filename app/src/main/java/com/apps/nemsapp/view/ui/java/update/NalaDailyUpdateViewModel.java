package com.apps.nemsapp.view.ui.java.update;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NalaDailyUpdateViewModel extends ViewModel {

    private final NalaDailyUpdateRepo nalaDailyListRepo;
    private final MutableLiveData<NalaDailyUpdateModel> liveData;
    private final MutableLiveData<String> message;

    public NalaDailyUpdateViewModel() {
        nalaDailyListRepo = new NalaDailyUpdateRepo();
        liveData = nalaDailyListRepo.getModelMutableLiveData();
        message = nalaDailyListRepo.getStringMutableLiveData();
    }


    public void getUpdateDailyData(String userid, String ulbid,
                                   String wardid, String circleid, String apkversion,
                                   String status, String loginid,
                                   String zoneid, String today_tot_qty, String today_length,
                                   String today_width, String today_depth, String workid,
                                   String today_qty,String time,String lat,String longi,
                                   NalaDailyUpdateActivity mainActivity) {

        nalaDailyListRepo.getNalaDailyUdate(userid,
                ulbid, wardid, circleid, apkversion,status,loginid,zoneid,today_tot_qty,today_length,today_width,
                today_depth,workid,today_qty,time,lat,longi,mainActivity);

    }


    public MutableLiveData<NalaDailyUpdateModel> getDailyUpdateStatus() {
        return liveData;
    }

    public MutableLiveData<String> getMessageToShow() {
        return message;
    }
}
