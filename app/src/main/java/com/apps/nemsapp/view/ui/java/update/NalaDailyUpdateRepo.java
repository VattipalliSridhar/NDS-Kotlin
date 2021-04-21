package com.apps.nemsapp.view.ui.java.update;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apps.nemsapp.view.ui.java.api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NalaDailyUpdateRepo {

    private final MutableLiveData<NalaDailyUpdateModel> modelMutableLiveData;
    private final MutableLiveData<String> messageToShow;
    private final api retrofitApis;


    public NalaDailyUpdateRepo() {

        modelMutableLiveData = new MutableLiveData<>();
        messageToShow = new MutableLiveData<>();
        retrofitApis = api.Factory.create();

    }

    public void getNalaDailyUdate(String userid, String ulbid,
                                  String wardid, String circleid, String apkversion,
                                  String status, String loginid,
                                  String zoneid, String today_tot_qty, String today_length,
                                  String today_width, String today_depth, String workid,
                                  String today_qty,String time,String lat,String longi,
                                  NalaDailyUpdateActivity mainActivity) {

        Call<NalaDailyUpdateModel> scanResponseModelCall = retrofitApis.getUpdateDailyData(
                userid,
                ulbid, wardid, circleid, apkversion,status,loginid,zoneid,today_tot_qty,today_length,today_width,
                today_depth,workid,today_qty,time,lat,longi
        );
        scanResponseModelCall.enqueue(new Callback<NalaDailyUpdateModel>() {
            @Override
            public void onResponse(@NonNull Call<NalaDailyUpdateModel> call, @NonNull Response<NalaDailyUpdateModel> response) {
                if (response.isSuccessful()) {
                    NalaDailyUpdateModel nalaDailyModel = response.body();

                    if (nalaDailyModel != null) {

                        modelMutableLiveData.setValue(nalaDailyModel);
                    } else {
                        messageToShow.setValue(response.message());
                    }

                } else {

                    messageToShow.setValue(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NalaDailyUpdateModel> call, @NonNull Throwable error) {
                messageToShow.setValue("Something went wrong! please contact your administrator");
            }
        });


    }


    public MutableLiveData<NalaDailyUpdateModel> getModelMutableLiveData() {
        return modelMutableLiveData;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        if (messageToShow == null)
            messageToShow.setValue("");
        return messageToShow;
    }
}
