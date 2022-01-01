package com.example.easycarpoolapp.auth

import com.example.easycarpoolapp.auth.domain.User
import com.example.easycarpoolapp.auth.dto.JoinDto
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.auth.dto.LoginDto
import com.example.easycarpoolapp.auth.dto.TokenDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/api/user/signup")
    public fun getSignUpCall(@Body joinDto : JoinDto) : Call<ResponseBody>

    @POST("/api/auth/authenticate")
    public fun getLoginCall(@Body loginDto: LoginDto) : Call<LocalUserDto>

    @POST("/api/auth/getUserData")
    public fun getUserDataCall(@Body token: TokenDto) : Call<LocalUserDto>

}