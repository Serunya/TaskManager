package com.example.taskmanager.api

import com.example.taskmanager.payload.request.PointsRequest
import com.example.taskmanager.payload.response.PointResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PointApi {
    @GET("/api/point/getPoints")
    suspend fun getAllPoint(@Query("taskId") taskId : Int): Response<Array<PointResponse>>
    @POST("/api/point/addPoints")
    suspend fun addPoints(@Body points : PointsRequest) : Response<String>


    companion object{
        fun getApi() : PointApi?{
            return ApiClient.client?.create(PointApi::class.java)
        }
    }



}