package com.example.easycarpoolapp.auth.dto

import com.google.gson.annotations.SerializedName

class LoginDto(email : String, password : String){

    @SerializedName("email")
    val email : String = email

    @SerializedName("password")
    val password : String = password
}




