package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.DashBoardModel
import com.apps.nemsapp.model.StatusModel
import com.apps.nemsapp.view.repoository.StatusRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class StatusViewModel : ViewModel() {

    private lateinit var response: Response<StatusModel>
    private var statusRepository: StatusRepository = StatusRepository()

    var statusLiveData = MutableLiveData<StatusModel>()
    var messageShow = MutableLiveData<String>()


    fun getStatusData(userId: String, ulbId: String, versionCode: Int) {

        viewModelScope.launch {
            response = statusRepository.getdata(userId,ulbId,versionCode.toString())

            if(response.isSuccessful)
            {
                statusLiveData.value = response.body()
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