package com.example.easycarpoolapp.navigation.progress.dto

import com.google.gson.annotations.SerializedName


data class PostReviewDto(
    @SerializedName("postId")
    val postId : Long,

    @SerializedName("host_email")
    val host_email : String,

    @SerializedName("host_nickname")
    val host_nickname : String,

    @SerializedName("writer_email")
    val writer_email : String,

    @SerializedName("writer_nickname")
    val writer_nickname : String,

    @SerializedName("rate")
    val rate : Float,

    @SerializedName("title")
    val title : String,

    @SerializedName("content")
    val content : String,
)
