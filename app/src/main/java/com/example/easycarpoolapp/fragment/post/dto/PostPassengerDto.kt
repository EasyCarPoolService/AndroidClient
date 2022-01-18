package com.example.easycarpoolapp.fragment.post.dto

import com.google.gson.annotations.SerializedName


/*
1. 사용자 식별읠 위한 토큰은 헤더에 함께 전다 되므로 dto에 포함되지 않음.
2. 작성 시간등의 시간정보는 서버에서 database에 access하는 time으로 결정되므로 client에서 설정하지 않음.
3. 게시글 식별 번호는 서버에서 database에 access할때 결정되므로 client에서 설정하지 않음.
 */
data class PostPassengerDto(

    @SerializedName("email")
    val email : String,

    @SerializedName("nickname")
    val nickname : String,

    @SerializedName("gender")
    val gender : String,

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
    val message : String
)