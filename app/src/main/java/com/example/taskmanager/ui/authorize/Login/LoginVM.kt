package com.example.taskmanager.ui.authorize.Login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.api.ApiClient
import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.repository.TokenRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginVM(private val apl: Application) : AndroidViewModel(apl){
    val repository = LoginRepository()
    val tokenRepository  = TokenRepository(apl.applicationContext)
    val loginResult : MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    init {
        ApiClient.access_token = tokenRepository.getAccessToken()
        ApiClient.refresh_token = tokenRepository.getRefreshToken()
        ApiClient.tokenRepository = tokenRepository
    }

    fun loginUser(login: String, pass : String){
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try{
                val loginRequest = LoginRequest(login, pass)
                val response = repository.loginUser(loginRequest)
                if (response?.code() == 200){
                    loginResult.value = BaseResponse.Success(response.body());
                }
                else{
                    loginResult.value =
                        response?.code()?.let { BaseResponse.Error(response.message(), it) }
                }
            }catch (ex : Exception){
                loginResult.value = BaseResponse.Error(ex.message,404)
            }
        }
    }
}