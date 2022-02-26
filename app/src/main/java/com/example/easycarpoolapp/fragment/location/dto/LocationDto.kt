package com.example.easycarpoolapp.fragment.location.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("writer")
    var writer : String,

    @SerializedName("lon")
    var lon : Double,

    @SerializedName("lat")
    var lat : Double,
    )