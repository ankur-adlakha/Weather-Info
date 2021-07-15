package com.ankuradlakha.weather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Sys(
    @Expose
    @SerializedName("type")
    var type: Int?,
    @Expose
    @SerializedName("Long")
    var id: Int?,
    @Expose
    @SerializedName("Country")
    var country: String?,
    @Expose
    @SerializedName("sunrise")
    var sunrise: Long?,
    @Expose
    @SerializedName("sunset")
    var sunset: Long?,
)
