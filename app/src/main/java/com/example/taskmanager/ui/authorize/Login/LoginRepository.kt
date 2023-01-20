package com.example.taskmanager.ui.authorize.Login

import com.example.taskmanager.api.LoginApi
import com.example.taskmanager.api.UserApi
import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.payload.response.UserResponse
import retrofit2.Response

class LoginRepository {

    suspend fun loginUser(loginRequset: LoginRequest): Response<LoginResponse>? {
        return LoginApi.getApi()?.loginUser(loginRequset)
    }
}