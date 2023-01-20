package com.example.taskmanager.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.taskmanager.R

class TokenRepository(val context: Context) {

    private val ACCESS_TOKEN = "ACCESS_TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"


    fun saveRefreshToken(token: String) {
        saveString(context, REFRESH_TOKEN, token)
    }

    fun getRefreshToken() : String {
        val refreshToken = getString(context,REFRESH_TOKEN)
        return getString(context,REFRESH_TOKEN) ?: ""
    }


    fun saveAccessToken(token: String) {
        saveString(context, ACCESS_TOKEN, token)
    }

    fun getAccessToken(): String {
        return getString(context, ACCESS_TOKEN) ?: ""
    }

    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()

    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun clearData(){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }
}
