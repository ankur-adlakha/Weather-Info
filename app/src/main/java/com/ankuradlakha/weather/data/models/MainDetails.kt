package com.ankuradlakha.weather.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainDetails(
    @Expose
    @SerializedName("temp")
    var temp: Double?,
    @Expose
    @SerializedName("feels_like")
    var feelsLike: Double?,
    @Expose
    @SerializedName("temp_min")
    var tempMin: Double?,
    @Expose
    @SerializedName("temp_max")
    var tempMax: Double?,
    @Expose
    @SerializedName("pressure")
    var pressure: Double?,
    @Expose
    @SerializedName("humidity")
    var humidity: Double?
)
