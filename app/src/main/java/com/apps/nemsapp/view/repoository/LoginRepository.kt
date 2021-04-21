package com.apps.nemsapp.view.repoository

import com.apps.nemsapp.model.LoginModel
import com.apps.nemsapp.view.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginRepository {

    suspend fun getLoginData(user: String, pass: String, versionCode: String): Response<LoginModel> {

        return withContext(Dispatchers.IO) {
            RetrofitClient.apiInterface.login(user, pass, versionCode)
        }
    }
}