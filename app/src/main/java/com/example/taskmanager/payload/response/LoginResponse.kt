package com.example.taskmanager.payload.response

data class LoginResponse(
    val access_token : String,
    val refresh_token : String
)
