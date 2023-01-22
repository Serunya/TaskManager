package com.example.taskmanager.ui.mainmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.payload.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.Exception

class MainMenuVM() : ViewModel() {
    val taskResult: MutableLiveData<BaseResponse<Array<TaskResponse>>> = MutableLiveData()
    val usernameResult: MutableLiveData<BaseResponse<UserResponse>> = MutableLiveData()
    val allUserNameResult: MutableLiveData<BaseResponse<Array<UserResponse>>> = MutableLiveData()

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

    fun getAllUsernameResponse() {
        allUserNameResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = getAllUsername()
                if (response?.code() == 200) {
                    allUserNameResult.value = BaseResponse.Success(response.body())
                } else {
                    allUserNameResult.value = BaseResponse.Error(
                        response?.message() ?: "Неизвестная ошибка",
                        response?.code() ?: 404
                    )
                }
            }catch (ex : Exception){
                allUserNameResult.value = BaseResponse.Error(ex.message ?: "Неизвестная ошибка",404)
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

    private suspend fun getUserName(): Response<UserResponse>? {
        return UserApi.getApi()?.getUserName()
    }

    private suspend fun getAllUsername(): Response<Array<UserResponse>>? {
        return UserApi.getApi()?.getAllUser()
    }

}