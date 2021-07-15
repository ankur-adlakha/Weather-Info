package com.ankuradlakha.weather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Rain(
    @Expose
    @SerializedName("1h")
    var oneHour: String?,
    @Expose
    @SerializedName("3h")
    var threeHour: String?
)