package com.example.easycarpoolapp.fragment.post

import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PostAPI {

    @POST("/api/post/save")
    public fun getSaveCall(@Body postPassengerDto: PostPassengerDto) : Call<ResponseBody>





}