package com.example.easycarpoolapp.fragment.post

import com.example.easycarpoolapp.auth.domain.User
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PostAPI {

    //태워주세요 게시글 등록
    @POST("/api/post/passenger/register")
    public fun getPassengerSaveCall(@Body postPassengerDto: PostPassengerDto) : Call<ResponseBody>

    //타세요 게시글 등록
    @POST("/api/post/driver/register")
    public fun getDriverSaveCall(@Body postDriverDto: PostDriverDto) : Call<ResponseBody>


    //태워주세요 게시글 조회
    @GET("/api/post/passenger/getPost")
    public fun getPassengerPostCall() : Call<ArrayList<PostDto>>

    //타세요 게시글 조회
    @GET("/api/post/driver/getPost")
    public fun getDriverPostCall() : Call<ArrayList<PostDto>>

    //User가 작성한 게시글 조회
    @POST("/api/post/user/getPost")
    fun getUserPostCall(@Body localUserDto: LocalUserDto): Call<ArrayList<PostDto>>

    // 지역명 기반 게시글 조회
    @POST("/api/post/getPostByDistrict")
    fun getPostByDistrictCall(@Body postDistrictDto: PostDistrictDto): Call<ArrayList<PostDto>>

    @POST("/api/chat/createroom")
    public fun getCreateRoomCall(@Body chatRoomDto: ChatRoomDto) : Call<ChatRoomDto>


    @POST("/api/user/getDriverAuth")
    public fun getDriverAuthCall(@Body localUserDto : LocalUserDto) : Call<LocalUserDto>

    @POST("/api/post/getUserPostData")
    public fun getUserPostDataCall(@Body localUserDto: LocalUserDto): Call<UserPostDto>




}