package com.example.easycarpoolapp.auth.dto

import com.example.easycarpoolapp.auth.domain.User
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Member

class JoinDto(name : String, email : String, nickname : String, password :String, birth : String, gender : String, role : String) {

    val name : String = name
    val email : String = email
    val nickname : String = nickname
    val password : String = password
    val birth : String = birth
    val gender : String = gender
    val role : String = role

    public fun toUserEntity() : User {
           return User(name, email, nickname, password, birth, gender, role)
    }

}