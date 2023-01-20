package com.example.taskmanager.ui.mainmenu

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.api.TaskController
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.payload.response.UserResponse
import com.example.taskmanager.ui.authorize.AuthActivity
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainMenuVM() : ViewModel() {
    val taskResult: MutableLiveData<BaseResponse<Array<TaskResponse>>> = MutableLiveData()
    val usernameResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()

    fun requestUserName() {
        usernameResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = getUserName()
                if (response?.code() == 200) {
                    usernameResult.value = BaseResponse.Success(response.body())
                } else {
                    usernameResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            } catch (ex: Exception) {
                usernameResult.value = BaseResponse.Error(ex.message, 404)
            }
        }
    }


    fun getTask() {
        taskResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = getAllTask()
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

    private suspend fun getAllTask(): Response<Array<TaskResponse>>? {
        return TaskApi.getApi()?.getAllTask()
    }

    private suspend fun getUserName() : Response<UserResponse>? {
        return UserApi.getApi()?.getUserName()
    }

}