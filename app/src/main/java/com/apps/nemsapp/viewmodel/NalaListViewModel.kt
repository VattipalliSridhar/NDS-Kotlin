package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.NalaListModel
import com.apps.nemsapp.view.repoository.NalaListRepository
import com.apps.nemsapp.view.ui.activities.NallaListActivity
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class NalaListViewModel : ViewModel() {


    private lateinit var response: Response<NalaListModel>
    private var nalaListRepository: NalaListRepository = NalaListRepository()

    var nalaListData = MutableLiveData<NalaListModel>()
    var messageShow = MutableLiveData<String>()

    fun getNalaData(
        userID: String,
        ulbId: String,
        circleId: String,
        wardId: String,
        versionCode: Int,
        statusId: String
    ) {

        viewModelScope.launch {

            response = nalaListRepository.getDataNalaList(
                userID,
                ulbId,
                circleId,
                wardId,
                versionCode,
                statusId
            )

            if (response.isSuccessful) {
                nalaListData.value = response.body()
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
}