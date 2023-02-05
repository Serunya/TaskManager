package com.example.taskmanager.payload.request

data class RegRequest(
    val login:String,
    val pass:String,
    val firstName:String,
    val secondName: String
)
