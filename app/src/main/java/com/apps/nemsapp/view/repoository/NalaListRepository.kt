package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.NalaListModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NalaListRepository {


    suspend fun getDataNalaList(
        userID: String,
        ulbId: String,
        circleId: String,
        wardId: String,
        versionCode: Int,
        statusId: String
    ): Response<NalaListModel> {


        return withContext(Dispatchers.IO) {
            RetrofitClient.apiInterface.nallaData(
                userID,
                ulbId,
                circleId,
                wardId,
                versionCode.toString(),
                statusId
            )
        }

    }


}