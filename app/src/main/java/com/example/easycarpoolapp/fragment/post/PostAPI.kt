package com.example.easycarpoolapp.fragment.post

import com.example.easycarpoolapp.auth.domain.User
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.PostDriverDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostAPI {

    @POST("/api/post/passenger/register")
    public fun getPassengerSaveCall(@Body postPassengerDto: PostPassengerDto) : Call<ResponseBody>

    @POST("/api/post/driver/register")
    public fun getDriverSaveCall(@Body postDriverDto: PostDriverDto) : Call<ResponseBody>

    @GET("/api/post/passenger/getPost")
    public fun getPassengerPostCall() : Call<ArrayList<PostDto>>

    @GET("/api/post/driver/getPost")
    public fun getDriverPostCall() : Call<ArrayList<PostDto>>

    @POST("/api/chat/createroom")
    public fun getCreateRoomCall(@Body chatRoomDto: ChatRoomDto) : Call<ChatRoomDto>


    @POST("/api/user/getdriverauth")
    public fun getDriverAuthCall(@Body localUserDto : LocalUserDto) : Call<LocalUserDto>

}