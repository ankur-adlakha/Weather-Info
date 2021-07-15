package com.ankuradlakha.weather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coord(
    @Expose
    @SerializedName("lat")
    var latitude:Double?,
    @Expose
    @SerializedName("lon")
    var longitude:Double?
)
