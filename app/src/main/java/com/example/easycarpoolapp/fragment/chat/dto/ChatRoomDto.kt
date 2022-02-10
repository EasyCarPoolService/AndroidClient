package com.example.easycarpoolapp.fragment.chat.dto

import com.google.gson.annotations.SerializedName

// server에서 시간, uuid등 생성하여 데이터베이스에 저장
// 어떠한 게시글에 대한 대화창인지 식별 여부 추후 판단

data class ChatRoomDto(
    @SerializedName("roomId") var roomId: String = "",
    @SerializedName("driver") var driver: String = "",
    @SerializedName("driverNickname") var driverNickname: String = "",
    @SerializedName("driverFcmToken") var driverFcmToken: String = "",
    @SerializedName("passenger") var passenger: String = "",
    @SerializedName("passengerNickname") var passengerNickname: String = "",
    @SerializedName("passengerFcmToken") var passengerFcmToken: String = ""

)