package com.example.easycarpoolapp.navigation.profile.report.dto

import com.google.gson.annotations.SerializedName

data class AccuseDto (
        @SerializedName("accuse_type")
        val accuse_type : String = "",

        @SerializedName("accused_nickname")
        val accused_nickname : String = "",

        @SerializedName("accuser_nickname")
        val accuser_nickname : String = "",

        @SerializedName("accuse_content")
        val accuse_content : String = ""

        )