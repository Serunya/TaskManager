package com.example.taskmanager.ui.addtask

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.PointApi
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.request.PointRequest
import com.example.taskmanager.payload.request.PointsRequest
import com.example.taskmanager.payload.request.TaskRequest
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.TaskIdResponse
import com.example.taskmanager.payload.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class AddTaskVM : ViewModel() {
    val saveResult = MutableLiveData<BaseResponse<Boolean>>()

    fun saveTask(taskRequest: TaskRequest,points : ArrayList<String>) {
        saveResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                Log.println(Log.INFO, "saveTask", "sendTask")
                val response = saveTask(taskRequest)
                if (response?.code() == 200){
                    savePoints(response.body()?.taskId ?: 0, points)
                }
                else{
                    saveResult.value = BaseResponse.Error(response?.message(),response?.code() ?: 404)
                }
            }catch (ex : Exception){
                Log.println(Log.ERROR,"saveTask", ex.message.toString())
                saveResult.value = BaseResponse.Error(ex.message,404)
            }
        }
    }

    private suspend fun saveTask(taskRequest: TaskRequest) : Response<TaskIdResponse>?{
        return TaskApi.getApi()?.addTask(taskRequest)
    }

    private fun savePoints(taskId: Int, pointsContent : ArrayList<String>) {
        viewModelScope.launch {
            Log.println(Log.INFO,"saveTask","sendPoints")
            val points = ArrayList<PointRequest>()
            for(item in pointsContent){
                points.add(PointRequest(item,taskId,false))
            }
            val pointRequest = PointsRequest(points)
            val response = addPoints(pointRequest)
            if(response?.code() == 200){
                saveResult.value = BaseResponse.Success(true)
            }
            else{
                saveResult.value = BaseResponse.Error(response?.message(),response?.code() ?: 404)
            }
        }
    }

    private suspend fun addPoints(pointsRequest: PointsRequest): Response<String>? {
        return PointApi.getApi()?.addPoints(pointsRequest)
    }

}