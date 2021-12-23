package com.example.easycarpoolapp.auth.domain

import com.google.gson.annotations.SerializedName


//data class Word(@SerializedName("eng") val eng:String,@SerializedName("kor") val kor:String)

data class User(
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("password") val password : String,
    @SerializedName("birth") val birth : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("role") val role : String
)

