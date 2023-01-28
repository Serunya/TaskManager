package com.example.taskmanager.payload.request

data class MessageRequest(
    val content : String,
    private val chatId: Int
)
