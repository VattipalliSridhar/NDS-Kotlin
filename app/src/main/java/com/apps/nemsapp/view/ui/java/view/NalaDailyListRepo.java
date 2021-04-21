package com.apps.nemsapp.view.ui.java.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apps.nemsapp.view.ui.java.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NalaDailyListRepo {

    private final MutableLiveData<NalaDailyModel> modelMutableLiveData;
    private final MutableLiveData<String> messageToShow;
    private final api retrofitApis;


    public NalaDailyListRepo() {

        modelMutableLiveData = new MutableLiveData<>();
        messageToShow = new MutableLiveData<>();
        retrofitApis = api.Factory.create();

    }

    public void getNalaDailyList(String userid, String ulbid,
                            String wardid, String circleid, String apkversion,
                            NalaDailyListActivityJava mainActivity){

        Call<NalaDailyModel> scanResponseModelCall = retrofitApis.getDailyData(userid,ulbid,wardid,circleid,apkversion);
        scanResponseModelCall.enqueue(new Callback<NalaDailyModel>() {
            @Override
            public void onResponse(@NonNull Call<NalaDailyModel> call, @NonNull Response<NalaDailyModel> response) {
                if (response.isSuccessful()) {
                    NalaDailyModel nalaDailyModel = response.body();

                    if (nalaDailyModel != null){

                        if (nalaDailyModel.getStatusCode() != null && nalaDailyModel.getStatusCode() == 200) {
                            modelMutableLiveData.setValue(nalaDailyModel);
                        } else {
                            messageToShow.setValue(nalaDailyModel.getStatusMessage());
                        }

                    }else {
                        messageToShow.setValue(response.message());
                    }

                } else {

                    messageToShow.setValue(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NalaDailyModel> call,@NonNull Throwable error) {
                messageToShow.setValue("Something went wrong! please contact your administrator");
            }
        });


    }


    public MutableLiveData<NalaDailyModel> getModelMutableLiveData() {
        return modelMutableLiveData;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        if (messageToShow == null)
            messageToShow.setValue("");
        return messageToShow;
    }
}
