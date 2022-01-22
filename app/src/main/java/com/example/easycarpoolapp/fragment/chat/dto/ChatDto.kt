package com.example.easycarpoolapp.fragment.chat.dto

import com.google.gson.annotations.SerializedName

data class ChatDto (
    @SerializedName("roomId") var roomId : String="",

    @SerializedName("writer") var writer : String="",

    @SerializedName("message") var message : String=""
)


