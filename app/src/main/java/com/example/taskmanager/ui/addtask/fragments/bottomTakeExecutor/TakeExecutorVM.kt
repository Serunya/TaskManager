package com.example.taskmanager.ui.addtask.fragments.bottomTakeExecutor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class TakeExecutorVM : ViewModel() {
    val usersResult  = MutableLiveData<BaseResponse<Array<UserResponse>>>()
    val users = ArrayList<UserResponse>()


    public fun getUsers() {
        usersResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val response = getAllUser()
                if (response?.code() == 200){
                    usersResult.value = BaseResponse.Success(response.body());
                    users.clear()
                    users.addAll(response.body()?.toList() ?: ArrayList())
                }
                else{
                    usersResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            }catch (ex : Exception){
                usersResult.value = BaseResponse.Error(ex.message,404)
            }
        }
    }


    suspend fun getAllUser() : Response<Array<UserResponse>>? {
        return UserApi.getApi()?.getAllUser()
    }
}