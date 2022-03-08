package com.example.easycarpoolapp.fragment.post.dto

import com.google.gson.annotations.SerializedName

data class PostDto(

    @SerializedName("postId")
    var postId : Long,

    @SerializedName("type")
    val type : String,

    @SerializedName("email")
    val email : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("gender")
    val gender : String,

    @SerializedName("rate")
    val rate : Float,

    @SerializedName("departure")
    val departure : String,

    @SerializedName("destination")
    val destination : String,

    @SerializedName("departureDate")
    val departureDate :String,

    @SerializedName("departureTime")
    val departureTime : String,

    @SerializedName("gift")
    val gift : String,

    //@SerializedName("hashTag")   //추후 hashTag구현 여부 판단
    //val hashTag : String,

    @SerializedName("message")
    val message : String,

    @SerializedName("fcmToken")
    val fcmToken : String
)
