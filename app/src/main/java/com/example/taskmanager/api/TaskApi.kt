package com.example.taskmanager.api

import com.example.taskmanager.payload.request.LoginRequest
import com.example.taskmanager.payload.request.TaskRequest
import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.payload.response.TaskIdResponse
import com.example.taskmanager.payload.response.TaskResponse
import retrofit2.Response
import retrofit2.http.*

interface TaskApi {

    @GET("/api/task/getAllTask")
    suspend fun getAllTask(): Response<Array<TaskResponse>>
    @GET("/api/task/getTaskById")
    suspend fun getTaskById(@Query("id") id : Int) : Response<TaskResponse>
    @POST("/api/task/upgradeStatus")
    suspend fun upgradeStatus(@Query("taskId") taskId : Int)
    @POST("/api/task/add")
    suspend fun addTask(@Body taskRequest : TaskRequest) : Response<TaskIdResponse>



    companion object{
        fun getApi() : TaskApi?{
            return ApiClient.client?.create(TaskApi::class.java)
        }
    }
}