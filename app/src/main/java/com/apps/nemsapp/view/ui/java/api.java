package com.apps.nemsapp.view.ui.java;

import com.apps.nemsapp.view.base.Constants;
import com.apps.nemsapp.view.ui.java.update.NalaDailyUpdateModel;
import com.apps.nemsapp.view.ui.java.view.NalaDailyModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface api {

    class Factory {
        public static api create() {
            //create logger
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // default time out is 15 seconds
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(api.class);
        }
    }

    @FormUrlEncoded
    @POST("nalla-work-list")
    Call<NalaDailyModel> getDailyData(@Field("user_id") String user_id,
                                      @Field("ulbid") String ulbid,
                                      @Field("ward_id") String ward_id,
                                      @Field("circle_id") String circle_id,
                                      @Field("apk_version") String apk_version
    );

    @FormUrlEncoded
    @POST("saveDailyQtydata")
    Call<NalaDailyUpdateModel> getUpdateDailyData(@Field("user_id") String user_id,
                                                  @Field("ulbid") String ulbid,
                                                  @Field("ward_id") String ward_id,
                                                  @Field("circle_id") String circle_id,
                                                  @Field("apk_version") String apk_version,
                                                  @Field("status") String status,
                                                  @Field("login_id") String login_id,
                                                  @Field("zone_id") String zone_id,
                                                  @Field("today_total_qty") String today_total_qty,
                                                  @Field("total_length_today") String total_length_today,
                                                  @Field("total_width_today") String total_width_today,
                                                  @Field("total_depth_today") String total_depth_today,
                                                  @Field("Workid") String Workid,
                                                  @Field("total_qty") String total_qty,
                                                  @Field("appTime") String appTime,
                                                  @Field("lat") String lat,
                                                  @Field("lng") String lng
                                                  );
}

