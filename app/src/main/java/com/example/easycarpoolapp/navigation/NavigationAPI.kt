package com.example.easycarpoolapp.navigation

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
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
        @Part testData : MultipartBody.Part
    ) : Call<ResponseBody>

}