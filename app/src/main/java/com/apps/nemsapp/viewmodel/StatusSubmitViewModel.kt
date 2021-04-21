package com.apps.nemsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.nemsapp.model.StatusSubmitModel
import com.apps.nemsapp.view.repoository.StatusSubmitRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.File

class StatusSubmitViewModel : ViewModel() {


    private lateinit var response: Response<StatusSubmitModel>
    private var statusSubmitRepository : StatusSubmitRepository = StatusSubmitRepository()

    var dashLiveData = MutableLiveData<StatusSubmitModel>()
    var messageShow  = MutableLiveData<String>()


    fun submitData(
        userId: String,
        ulbid: String,
        circle_id: String,
        login_id: String,
        nalla_id: String,
        ward_id: String,
        status: String,
        zone_id: String,
        lat: String,
        lng: String,
        appTime: String,
        todayQty: String,
        yesterdayData: String,
        total: String,
        img: String,
        workid: String
    ) {

        var photoFile: MultipartBody.Part
        val capturePhoto = File(img)
        photoFile = if (capturePhoto.exists()) {
            val requestBody = capturePhoto.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file_img", capturePhoto.name, requestBody)
        } else {
            val attachmentEmpty = "".toRequestBody("text/plain".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file_img", "", attachmentEmpty)
        }



        val userIdd: RequestBody = userId.toRequestBody(userId.toMediaTypeOrNull())
        val ulbId: RequestBody = ulbid.toRequestBody(ulbid.toMediaTypeOrNull())
        val circleId: RequestBody = circle_id.toRequestBody(circle_id.toMediaTypeOrNull())
        val loginId: RequestBody = login_id.toRequestBody(login_id.toMediaTypeOrNull())
        val nallaId: RequestBody = nalla_id.toRequestBody(nalla_id.toMediaTypeOrNull())
        val wardId: RequestBody = ward_id.toRequestBody(ward_id.toMediaTypeOrNull())
        val statusId: RequestBody = status.toRequestBody(status.toMediaTypeOrNull())
        val zoneId: RequestBody = zone_id.toRequestBody(zone_id.toMediaTypeOrNull())
        val latitude: RequestBody = lat.toRequestBody(lat.toMediaTypeOrNull())
        val langg: RequestBody = lng.toRequestBody(lng.toMediaTypeOrNull())
        val time: RequestBody = appTime.toRequestBody(appTime.toMediaTypeOrNull())
        val todayDone: RequestBody = todayQty.toRequestBody(todayQty.toMediaTypeOrNull())
        val yesterdayQty: RequestBody = yesterdayData.toRequestBody(yesterdayData.toMediaTypeOrNull())
        val totalQty: RequestBody = total.toRequestBody(total.toMediaTypeOrNull())
        val workID: RequestBody = workid.toRequestBody(workid.toMediaTypeOrNull())

        viewModelScope.launch {

            response = statusSubmitRepository.getData(userIdd,ulbId,circleId,loginId,nallaId,
                wardId,statusId,zoneId,latitude,langg,time,todayDone,yesterdayQty,totalQty,photoFile,workID)

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