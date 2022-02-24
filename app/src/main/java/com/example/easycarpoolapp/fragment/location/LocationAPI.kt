package com.example.easycarpoolapp.fragment.location


import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface LocationAPI {

    @POST("/api/location/getReservedPost")
    public fun getReservedPostCall(@Body localUserDto : LocalUserDto) : Call<ArrayList<ReservedPostDto>>

}