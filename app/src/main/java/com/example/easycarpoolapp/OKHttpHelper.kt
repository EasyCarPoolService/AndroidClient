package com.example.easycarpoolapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient

class OKHttpHelper {

    companion object{

        private fun getTokenSharedPreference(context : Context) : String{
            val sharedPreference = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
            val token = sharedPreference.getString("token", "default")
            return token!!
        }//getTokenSharedPreference

        private fun createInterceptor(token : String): Interceptor {


            return Interceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                val response = chain.proceed(newRequest)
                response.newBuilder().build()
            }
        }//createInterceptor

        public fun createHttpClient(context : Context): OkHttpClient {
            val token = getTokenSharedPreference(context)

            Log.e("OKHTTPHelper", token.toString())

            return OkHttpClient.Builder()
                .addNetworkInterceptor(createInterceptor(token))
                .build()
        }//createHttpClient


        public fun createGson(): Gson? {
            return GsonBuilder()
                .setLenient()
                .create()
        }//createGson


    }

}