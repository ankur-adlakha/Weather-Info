package com.ankuradlakha.weather.data.repositories

import com.ankuradlakha.weather.BuildConfig
import com.ankuradlakha.weather.data.AppCache
import com.ankuradlakha.weather.data.AppDatabase
import com.ankuradlakha.weather.data.models.LatLngWrapper
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.network.API
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response

class LocationRepository(
    private val appDatabase: AppDatabase,
    private val api: API,
    private val appCache: AppCache
) {
    fun saveLatLng(lat: Double, lng: Double) {
        val locationDao = appDatabase.getLocationDao()
        locationDao.deleteLatLng(lat, lng)
        locationDao.saveLatLng(LatLngWrapper(lat, lng))
    }

    fun getAllLatLngs() = appDatabase.getLocationDao().getAllLatLngs()
    fun getWeather(lat: Double?, lng: Double?): Response<WeatherResponse> =
        api.getWeather(
            lat ?: 0.0,
            lng ?: 0.0,
            BuildConfig.WEATHER_API_KEY,
            appCache.getUnitOfMeasurement()
        ).execute()

    fun getUnitForMeasurement() = appCache.getUnitOfMeasurement()
    fun deleteWeatherInfo(latLng: LatLng) {
        appDatabase.getLocationDao().deleteLatLng(latLng.latitude, latLng.longitude)
    }

    fun saveUnitOfMeasurement(unitOfMeasurement: String) {
        appCache.saveUnitOfMeasurement(unitOfMeasurement)
    }

    fun deleteAllWeatherInfo() {
        appDatabase.getLocationDao().deleteAllLatLngs()
    }
}