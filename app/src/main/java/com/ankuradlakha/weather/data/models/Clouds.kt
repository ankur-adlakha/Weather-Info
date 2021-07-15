package com.ankuradlakha.weather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Clouds(
    @Expose
    @SerializedName("all")
    var all:Int?
)
