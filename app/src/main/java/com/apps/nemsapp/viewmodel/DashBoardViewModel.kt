package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.DashBoardModel
import com.apps.nemsapp.view.repoository.DashBoardRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class DashBoardViewModel : ViewModel() {


    private lateinit var response: Response<DashBoardModel>
    private var dashBoardRepository : DashBoardRepository = DashBoardRepository()

    var dashLiveData = MutableLiveData<DashBoardModel>()
    var messageShow  = MutableLiveData<String>()

    fun getDashBoard(userId: String, ulbId: String, circleId: String, wardId: String, versionCode: Int, idd: String) {

        viewModelScope.launch {

            response = dashBoardRepository.getDashData(userId,ulbId,circleId,wardId,versionCode,idd)

            if(response.isSuccessful)
            {
                dashLiveData.value = response.body()
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