package com.example.taskmanager.api

import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.request.RefreshRequset
import com.example.taskmanager.payload.request.RegRequest
import com.example.taskmanager.payload.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/api/auth/signin")
    suspend fun loginUser(@Body loginRequest: LoginRequest):Response<LoginResponse>


    @POST("/api/auth/refreshtoken")
    suspend fun refreshToken(@Body refreshtoken:RefreshRequset) : Response<LoginResponse>

    @POST("/api/auth/signup")
    suspend fun regUser(@Body regRequest: RegRequest) : Response<String>


    companion object{
        fun getApi() : LoginApi?{
            return ApiClient.client?.create(LoginApi::class.java)
        }
    }
}