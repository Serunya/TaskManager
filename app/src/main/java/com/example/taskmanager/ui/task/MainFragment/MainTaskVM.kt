package com.example.taskmanager.ui.task.MainFragment

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.PointApi
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.payload.request.PointStatusRequest
import com.example.taskmanager.payload.response.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class MainTaskVM : ViewModel() {

    val pointResult = MutableLiveData<BaseResponse<Boolean>>()

    fun sendPointListener(pointId: Int, check: Boolean){
        viewModelScope.launch(Dispatchers.IO){
            try {
                editPointStatus(PointStatusRequest(pointId,check))
            } catch (ex: Exception) {
                Log.println(Log.ERROR,"Point", ex.message.toString())
                pointResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }


    private suspend fun editPointStatus(pointStatusRequest: PointStatusRequest){
        PointApi.getApi()?.editPointStatus(pointStatusRequest)
    }
}