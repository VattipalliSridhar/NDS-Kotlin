package com.apps.nemsapp.view.api


import com.apps.nemsapp.model.LoginModel
import com.apps.nemsapp.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {


    @FormUrlEncoded
    @POST("login")
    suspend fun login(
            @Field("username") user: String,
            @Field("password") password: String,
            @Field("apk_version") access_key: String
    ): Response<LoginModel>

    @FormUrlEncoded
    @POST("dashboard")
    suspend fun dashBoard(
            @Field("user_id") user: String,
            @Field("ulbid") ulbId: String,
            @Field("circle_id") circleId: String,
            @Field("ward_id") wardId: String,
            @Field("apk_version") apkVersion: String,
            @Field("id") ids: String
    ): Response<DashBoardModel>


    @FormUrlEncoded
    @POST("nalla-list")
    suspend fun nallaData(
            @Field("user_id") user: String,
            @Field("ulbid") ulbId: String,
            @Field("circle_id") circleId: String,
            @Field("ward_id") wardId: String,
            @Field("apk_version") apkVersion: String,
            @Field("status") status: String
    ): Response<NalaListModel>


    @FormUrlEncoded
    @POST("status-list")
    suspend fun statusSpinner(
            @Field("user_id") user: String,
            @Field("ulbid") ulbId: String,
            @Field("apk_version") access_key: String
    ): Response<StatusModel>


    @Multipart
    @POST("save-geotagging-data")
    suspend fun saveCleanNallaData(
            @Part("user_id") userId: RequestBody,
            @Part("ulbid") ulbId: RequestBody,
            @Part("circle_id") circleId: RequestBody,
            @Part("login_id") loginId: RequestBody,
            @Part("nalla_id") nalaId: RequestBody,
            @Part("ward_id") wardid: RequestBody,
            @Part("status") statusId: RequestBody,
            @Part("zone_id") zoneId: RequestBody,
            @Part("lat") latt: RequestBody,
            @Part("lng") lngg: RequestBody,
            @Part("appTime") time: RequestBody,
            @Part("today_total_qty") today_total_qty: RequestBody,
            @Part("yesterday_total_qty") yesterday_total_qty: RequestBody,
            @Part("total_qty") total_qty: RequestBody,
            @Part fileToUpload: MultipartBody.Part,
            @Part("Workid") workId: RequestBody
    ): Response<StatusSubmitModel>


    @FormUrlEncoded
    @POST("nalla-report")
    suspend fun nallaReportData(
        @Field("user_id") user: String,
        @Field("ulbid") ulbId: String,
        @Field("circle_id") circleId: String,
        @Field("ward_id") wardId: String,
        @Field("apk_version") apkVersion: String,
        @Field("nalla_id") nalaId: String
    ): Response<NalaReportModel>


}
