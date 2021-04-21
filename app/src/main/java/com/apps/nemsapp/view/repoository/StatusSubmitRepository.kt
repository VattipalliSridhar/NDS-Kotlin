package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.StatusSubmitModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class StatusSubmitRepository {

    suspend fun getData(
            userIdd: RequestBody,
            ulbId: RequestBody,
            circleId: RequestBody,
            loginId: RequestBody,
            nallaId: RequestBody,
            wardId: RequestBody,
            statusId: RequestBody,
            zoneId: RequestBody,
            latitude: RequestBody,
            langg: RequestBody,
            time: RequestBody,
            todayDone: RequestBody,
            yesterdayQty: RequestBody,
            totalQty: RequestBody,
            photoFile: MultipartBody.Part,
            workID: RequestBody
    ): Response<StatusSubmitModel> {

        return withContext(Dispatchers.IO) {
            RetrofitClient.apiInterface.saveCleanNallaData(
                    userIdd,
                    ulbId,
                    circleId,
                    loginId,
                    nallaId,
                    wardId,
                    statusId,
                    zoneId,
                    latitude,
                    langg,
                    time,
                    todayDone,
                    yesterdayQty,
                    totalQty,
                    photoFile,
                    workID
            )
        }

    }
}