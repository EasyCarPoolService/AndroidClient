package com.example.easycarpoolapp.auth

import com.example.easycarpoolapp.auth.domain.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/api/user/signup")
    public fun getSignUpCall(@Body user : User) : Call<ResponseBody>

}