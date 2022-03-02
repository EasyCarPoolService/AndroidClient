package com.example.easycarpoolapp.navigation

import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


//image전송할 수 있도록 변경
interface NavigationAPI {

    @Multipart
    @POST("/api/user/driverAuth")
    public fun getDriverAuthCall(
        @Part image_id: MultipartBody.Part,
        @Part image_car: MultipartBody.Part,
        @Part("email") email: RequestBody?,
        @Part("carNumber") carNumber: RequestBody?,
        @Part("manufacturer") manufacturer: RequestBody?,
        @Part("model") model : RequestBody?
    ) : Call<ResponseBody>

    @POST("/api/post/getUserPostData")
    public fun getUserPostDataCall(@Body localUserDto: LocalUserDto): Call<UserPostDto>


    @Multipart
    @POST("/api/user/editProfile")
    public fun getEditProfileCall(
        @Part("email") email: RequestBody?,
        @Part profile_image : MultipartBody.Part?,
        @Part("nickname") nickname: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("introduce_message") introduce_message: RequestBody?
    ) : Call<ResponseBody>

}