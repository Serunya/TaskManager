package com.example.taskmanager.payload.response

data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val executorId: Int,
    val curratorId: Int,
    val deadLine: String,
    val startingDate: String,
    val taskStatus : String)