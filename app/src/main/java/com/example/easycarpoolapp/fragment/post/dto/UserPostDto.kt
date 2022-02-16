package com.example.easycarpoolapp.fragment.post.dto

import com.google.gson.annotations.SerializedName

data class UserPostDto(
    @SerializedName("driver")
    val driver : String,

    @SerializedName("passenger")
    val passenger : String,

    @SerializedName("ongoing")
    val ongoing : String
)