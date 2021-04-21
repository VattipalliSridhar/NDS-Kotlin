package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.DashBoardModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit

class DashBoardRepository {


    suspend fun getDashData(userId: String, ulbId: String, circleId: String, wardId: String, versionCode: Int, idd: String): Response<DashBoardModel> {

        return withContext(Dispatchers.IO){
            RetrofitClient.apiInterface.dashBoard(userId,ulbId,circleId,wardId,versionCode.toString(),idd)
        }

    }
}