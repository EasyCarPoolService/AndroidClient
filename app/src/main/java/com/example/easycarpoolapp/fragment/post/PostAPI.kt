package com.example.easycarpoolapp.fragment.post

import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostAPI {

    @POST("/api/post/passenger/register")
    public fun getPassengerSaveCall(@Body postPassengerDto: PostPassengerDto) : Call<ResponseBody>

    @GET("/api/post/passenger/getPost")
    public fun getPassengerPostCall() : Call<ArrayList<PostPassengerDto>>

    @POST("/api/chat/createroom")
    public fun getCreateRoomCall(@Body chatRoomDto: ChatRoomDto) : Call<ChatRoomDto>

}