package com.example.taskmanager.payload.response

data class PointResponse(
    val id : Int,
    val content : String,
    val isDone : Boolean,
    val taskId : Int
)
