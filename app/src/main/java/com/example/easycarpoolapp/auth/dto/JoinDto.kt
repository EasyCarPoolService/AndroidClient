package com.example.easycarpoolapp.auth.dto

import com.example.easycarpoolapp.auth.domain.User
import com.google.gson.annotations.SerializedName

class JoinDto(name : String, email : String, nickname : String, password :String, birth : String, gender : String) {


    @SerializedName("name")
    val name : String = name

    @SerializedName("email")
    val email : String = email

    @SerializedName("nickname")
    val nickname : String = nickname

    @SerializedName("password")
    val password : String = password

    @SerializedName("birth")
    val birth : String = birth

    @SerializedName("gender")
    val gender : String = gender

    public fun toUserEntity() : User {
           return User(name, email, nickname, password, birth, gender)
    }

}