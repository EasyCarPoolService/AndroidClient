package com.example.easycarpoolapp.navigation.profile.report.dto

import com.google.gson.annotations.SerializedName

data class ReportDto(

    @SerializedName("report_title")
    val report_title : String = "",

    @SerializedName("report_content")
    val report_content : String = "",

    @SerializedName("report_user_email")
    val report_user_email : String = ""

)