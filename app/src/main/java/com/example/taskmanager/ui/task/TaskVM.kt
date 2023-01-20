package com.example.taskmanager.ui.task

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.PointApi
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.payload.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class TaskVM : ViewModel() {

    val taskResult: MutableLiveData<BaseResponse<TaskResponse>> = MutableLiveData()
    val pointResult: MutableLiveData<BaseResponse<Array<PointResponse>>> = MutableLiveData()
    val userResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val executorResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()

    private var task: TaskResponse? = null
    private var points: Array<PointResponse>? = null

    fun getUser() {
        userResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = user()
                if (response?.code() == 200) {
                    userResult.value = BaseResponse.Success(response.body())
                } else {
                    userResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            } catch (ex: Exception) {
                userResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }


    fun getTask(id: Int) {
        if (task?.id == id) {
            taskResult.value = BaseResponse.Success(task)
            return
        }
        taskResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = getTaskById(id)
                if (response?.code() == 200) {
                    taskResult.value = BaseResponse.Success(response.body())
                } else {
                    taskResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            } catch (ex: Exception) {
                taskResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }


    fun getAllPoints(taskId: Int) {
        if (task?.id == taskId) {
            pointResult.value = BaseResponse.Success(points)
            return
        }
        taskResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = getPoints(taskId)
                if (response?.code() == 200) {
                    pointResult.value = BaseResponse.Success(response.body())
                } else {
                    pointResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            } catch (ex: Exception) {
                pointResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }

    fun getExecutor(userId: Int){
        executorResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = userNameExecutor(userId)
                if (response?.code() == 200) {
                    executorResult.value = BaseResponse.Success(response.body())
                } else {
                    executorResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            } catch (ex: Exception) {
                executorResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }


    private suspend fun getTaskById(id: Int): Response<TaskResponse>? {
        return TaskApi.getApi()?.getTaskById(id);
    }

    private suspend fun getPoints(taskId: Int): Response<Array<PointResponse>>? {
        return PointApi.getApi()?.getAllPoint(taskId)
    }

    private suspend fun user(): Response<UserResponse>? {
        return UserApi.getApi()?.getUserName()
    }

    private suspend fun userNameExecutor(userId : Int) : Response<UserResponse>? {
        return UserApi.getApi()?.getUserById(userId)

    }
}