package com.example.taskmanager.ui.authorize.registry

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.LoginApi
import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.request.RegRequest
import com.example.taskmanager.payload.response.BaseResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class SignUpVM : ViewModel() {
    val regResult : MutableLiveData<BaseResponse<String>> = MutableLiveData()

    fun regUser(login: String, pass : String,firstName:String,secondName:String){
        regResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val regRequest = RegRequest(login, pass,firstName,secondName)
                val response = regUser(regRequest)
                if (response?.code() == 200){
                    regResult.value = BaseResponse.Success(response.body());
                }
                else{
                    regResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            }catch (ex : Exception){
                regResult.value = BaseResponse.Error(ex.message,404)
            }
        }
    }


    suspend fun regUser(regRequset: RegRequest): Response<String>? {
        return LoginApi.getApi()?.regUser(regRequset)
    }
}