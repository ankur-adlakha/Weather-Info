package com.ankuradlakha.weather.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @Expose
    @SerializedName("coord")
    var coord: Coord?,
    @Expose
    @SerializedName("weather")
    var weather: ArrayList<Weather>?,
    @Expose
    @SerializedName("base")
    var base: String?,
    @Expose
    @SerializedName("main")
    var main: MainDetails?,
    @Expose
    @SerializedName("visibility")
    var visibility: Long?,
    @Expose
    @SerializedName("wind")
    var wind: Wind?,
    @Expose
    @SerializedName("clouds")
    var clouds: Clouds?,
    @Expose
    @SerializedName("dt")
    var dt: Long?,
    @Expose
    @SerializedName("sys")
    var sys: Sys?,
    @Expose
    @SerializedName("timezone")
    var timezone: Long?,
    @Expose
    @SerializedName("id")
    var id: Long?,
    @Expose
    @SerializedName("name")
    var name: String?,
    @Expose
    @SerializedName("cod")
    var cod: Int?,
    @Expose
    @SerializedName("rain")
    var rain: Rain?,
    var latLngs : LatLng?
)
