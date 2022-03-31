package com.example.easycarpoolapp.fragment.post.dto

import com.google.gson.annotations.SerializedName

data class PostDistrictDto (

    @SerializedName("postType")
    val postType : String,

    @SerializedName("district")
    val district : String,
)