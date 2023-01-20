package com.example.taskmanager.payload.request

data class PointRequest(
    val content : String,
    val taskId : Int,
    val isDone : Boolean
)
