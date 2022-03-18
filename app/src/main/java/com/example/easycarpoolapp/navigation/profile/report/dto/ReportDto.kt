package com.example.easycarpoolapp.navigation.profile.report.dto

import com.google.gson.annotations.SerializedName

data class ReportDto(
    @SerializedName("name")
    val name : String = "",

    @SerializedName("email")
    val email : String = "",
)