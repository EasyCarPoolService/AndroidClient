package com.example.easycarpoolapp.auth.dto

import com.google.gson.annotations.SerializedName



//login, verifyToken수행의 결과로 응답받을 dto
// LocalUserDto를 기반으로 LocalUserData 객체를 생성
// AuthRepository에서 사용
data class LocalUserDto(
    @SerializedName("nickname")
    val nickname : String? = null,

    @SerializedName("email")
    val email : String? = null,

    @SerializedName("token")
    val token : String? = null,

    @SerializedName("gender")
    val gender : String? = null,

    @SerializedName("driverAuthentication")
    val driverAuthentication : Boolean? = null,

    @SerializedName("fcmToken")
    val fcmToken : String? = null

)


