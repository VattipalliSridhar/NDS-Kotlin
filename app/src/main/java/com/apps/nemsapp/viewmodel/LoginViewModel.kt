package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.LoginModel
import com.apps.nemsapp.view.repoository.LoginRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class LoginViewModel : ViewModel() {


    private lateinit var response : Response<LoginModel>
    private  var loginRepository: LoginRepository = LoginRepository()

    var loginLiveData = MutableLiveData<LoginModel>()
    var messageShow  = MutableLiveData<String>()


    fun getLogin(user: String, pass: String, accessKey: Int) {

        viewModelScope.launch {
            response = loginRepository.getLoginData(user,pass, accessKey.toString())
            if(response.isSuccessful)
            {
                loginLiveData.value = response.body()
            }else{
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let{
                    try{
                        message.append(JSONObject(it).getString("message"))
                    }catch(e: JSONException){ }
                    message.append("\n")
                }
                message.append("Error Code: ${response.code()}")
                messageShow.value = message.toString()
            }


        }

    }
}