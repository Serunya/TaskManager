package com.example.taskmanager.api

import com.example.taskmanager.payload.response.UserResponse

object UserController {
    private val users = HashMap<Int,String>()


    fun addUser(user : UserResponse){
        users.put(user.id,user.firstName + " " + user.secondName)
    }

    fun addUsers(users : Array<UserResponse>){
        for(user in users)
            addUser(user)
    }

    fun getUser(id : Int) : String {
        return users.get(id) ?: "Несуществующий пользователь"
    }
}