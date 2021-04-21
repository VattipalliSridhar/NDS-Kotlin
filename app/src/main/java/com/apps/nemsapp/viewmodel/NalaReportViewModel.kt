package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.NalaListModel
import com.apps.nemsapp.model.NalaReportModel
import com.apps.nemsapp.view.repoository.NalaReportRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class NalaReportViewModel : ViewModel() {


    fun getViewReport(
        userId: String,
        ulbId: String,
        circleId: String,
        wardId: String,
        versionCode: Int,
        nalaId: String
    ) {

        viewModelScope.launch {
            response = nalaReportRepository.getViuewReportData(
                userId,
                ulbId,
                circleId,
                wardId,
                versionCode.toString(),
                nalaId
            )
            if (response.isSuccessful) {
                nalaReportData.value = response.body()
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                    }
                    message.append("\n")
                }
                message.append("Error Code: ${response.code()}")
                messageShow.value = message.toString()
            }
        }




    }

    private lateinit var response: Response<NalaReportModel>
    private var nalaReportRepository: NalaReportRepository = NalaReportRepository()
    var nalaReportData = MutableLiveData<NalaReportModel>()
    var messageShow = MutableLiveData<String>()


}