package com.example.taskmanager.api

import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.payload.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/api/users/getUserName")
    suspend fun getUserName() : Response<UserResponse>
    @GET("/api/users/getUserById")
    suspend fun getUserById(@Query("userId") id : Int) : Response<UserResponse>
    @GET("/api/users/getAllUser")
    suspend fun getAllUser():Response<Array<UserResponse>>



    companion object{
        fun getApi() : UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

}