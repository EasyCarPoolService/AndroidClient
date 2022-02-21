package com.example.easycarpoolapp.fragment.calendar

import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CalendarAPI {

    @POST("/api/calendar/post")
    public fun getPostDataCall(@Body localUserDto: LocalUserDto) : Call<ArrayList<ReservedPostDto>>

}