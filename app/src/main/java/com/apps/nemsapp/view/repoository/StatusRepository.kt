package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.StatusModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class StatusRepository {
    suspend fun getdata(userId: String, ulbId: String, toString: String): Response<StatusModel> {


        return withContext(Dispatchers.IO){
            RetrofitClient.apiInterface.statusSpinner(userId,ulbId,toString)
        }



    }
}