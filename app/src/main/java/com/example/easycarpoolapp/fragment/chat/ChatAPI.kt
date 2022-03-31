package com.example.easycarpoolapp.fragment.chat

import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatAPI {



    @POST("/api/chat/getAllRoom")
    public fun getRoomCall(@Body localUserDto: LocalUserDto) : Call<ArrayList<ChatRoomDto>>

    @POST("api/chat/findMessageByRoomId")
    public fun getFindMessageCall(@Body roomDto : ChatRoomDto) : Call<ArrayList<ChatDto>>

    @POST("api/chat/findPostInfo")
    public fun getFindPostCall(@Body roomDto : ChatRoomDto) : Call<PostDto>

    @POST("api/chat/registerReservedPost")
    public fun getRegisterReservedPostCall(@Body reservedPostDto: ReservedPostDto): Call<String>

    @POST("api/chat/leaveChatRoom")
    fun getLeaveChatRoomCall(@Body chatRoomDto: ChatRoomDto): Call<String>


}