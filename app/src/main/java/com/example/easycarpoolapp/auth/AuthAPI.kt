package com.example.easycarpoolapp.auth

import com.example.easycarpoolapp.auth.domain.User
import com.example.easycarpoolapp.auth.dto.JoinDto
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.auth.dto.LoginDto
import com.example.easycarpoolapp.auth.dto.TokenDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthAPI {
    /*@POST("/api/user/signup")
    public fun getSignUpCall(@Body joinDto : JoinDto) : Call<ResponseBody>*/

    @POST("/api/auth/authenticate")
    public fun getLoginCall(@Body loginDto: LoginDto) : Call<LocalUserDto>

    @POST("/api/user/getUserData")
    public fun getUserDataCall() : Call<LocalUserDto>

    @Multipart
    @POST("/api/user/signup")
    public fun getSignUpCall(
        @Part profile_image : MultipartBody.Part?,
        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("nickname") nickname: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("birth") birth: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("fcmToken") fcmToken: RequestBody?
        ) : Call<ResponseBody>

}