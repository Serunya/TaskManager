package com.example.taskmanager.payload.response

import java.util.*

data class MessageResponse (
    val id : Long,
    val senderId : Long,
    val chatId : Long,
    val timestamp : Date,
    val content: String
)