package com.example.easycarpoolapp.auth.dto

import com.google.gson.annotations.SerializedName

class TokenDto(token : String) {

    @SerializedName("token")
    val token : String = token

}