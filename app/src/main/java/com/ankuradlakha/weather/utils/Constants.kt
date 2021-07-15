package com.ankuradlakha.weather.utils

const val argLat = "latitude"
const val argLng = "longitude"
const val argPlace = "place"
const val argWeather = "Weather"

enum class UnitOfMeasurement(
    val unit: String,
    val temperature: String,
    val wind: String,
    val rain: String
) {
    IMPERIAL("imperial", "F", "miles/hr", "mm"),
    METRIC("metric", "C", "mtr/s", "mm"),
    STANDARD("standard", "K", "mtr/s", "mm");
}