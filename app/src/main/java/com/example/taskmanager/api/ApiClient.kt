package com.example.taskmanager.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.taskmanager.constants.Constant
import com.example.taskmanager.payload.request.RefreshRequset
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.LoginResponse
import com.example.taskmanager.repository.TokenRepository
import com.example.taskmanager.ui.authorize.AuthActivity
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var access_token = ""
    var refresh_token = ""
    var tokenRepository : TokenRepository? = null
    var appContext : Context? = null


    var mRetrofit : Retrofit?  = null
    val client : Retrofit?
    get(){
        if(mRetrofit == null){
            mRetrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL).client(getClient())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return mRetrofit
    }

    fun getClient() : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request : Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + access_token).build()
                return chain.proceed(request);
            }
        }).authenticator(object : Authenticator{
            override fun authenticate(route: Route?, response: Response): Request? {
                refresh_token = tokenRepository?.getRefreshToken() ?: ""
                if (refresh_token.equals("")){
                    appContext?.startActivity(Intent(appContext, AuthActivity::class.java))
                        ?: Log.println(Log.ERROR,"Authefication", "Do not startAuthActivity")
                    return null;
                }
                else{
                    val refreshResponse = runBlocking {
                        return@runBlocking LoginApi.getApi()?.refreshToken(RefreshRequset(refresh_token))
                    }
                    if(refreshResponse?.code() == 200){
                        access_token = refreshResponse.body()?.access_token ?: ""
                        refresh_token = refreshResponse.body()?.refresh_token ?: ""
                        tokenRepository?.let {
                            it.saveAccessToken(access_token)
                            it.saveRefreshToken(refresh_token)
                        } ?: Log.println(Log.ERROR,"Authefication", "Not save Pair RefreshToken")
                        Log.println(Log.INFO, "Auth","New Token " + access_token)
                        return response.request.newBuilder()
                            .header("Authorization", "Bearer " + access_token).build()
                    }
                    else{
                        tokenRepository!!.clearData()
                        Log.println(Log.ERROR,"Authefication", "Not save Pair RefreshToken")
                        return null;
                    }
                }
            }
        }).build()
    }
}