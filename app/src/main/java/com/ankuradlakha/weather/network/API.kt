package com.ankuradlakha.weather.network

import com.ankuradlakha.weather.data.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String,
        @Query("units") unitOfMeasurement: String
    ): Call<WeatherResponse>
}