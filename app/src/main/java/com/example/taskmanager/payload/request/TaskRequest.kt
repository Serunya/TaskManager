package com.example.taskmanager.payload.request

data class TaskRequest(
    val title : String,
    val description : String,
    val executorId : Int,
    val deadLine : String
)
