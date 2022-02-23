package com.example.easycarpoolapp.fragment.chat.dto

import com.google.gson.annotations.SerializedName

data class ReservedPostDto(

    @SerializedName("postId")
    var postId : Long,

    @SerializedName("postType")
    var postType : String,

    @SerializedName("driver")
    val driver : String,

    @SerializedName("passenger")
    val passenger : String,

    @SerializedName("date")
    val date : String,

    @SerializedName("time")
    val time : String
)