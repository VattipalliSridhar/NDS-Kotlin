package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.NalaReportModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NalaReportRepository {


    suspend fun getViuewReportData(userId: String, ulbId: String, circleId: String, wardId: String, apkVersion: String, nalaId: String): Response<NalaReportModel> {


        return withContext(Dispatchers.IO){
            RetrofitClient.apiInterface.nallaReportData(userId,ulbId,circleId,wardId,apkVersion,nalaId)
        }
    }
}