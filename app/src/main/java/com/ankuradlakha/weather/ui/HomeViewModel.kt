package com.ankuradlakha.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankuradlakha.weather.data.Resource
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.data.repositories.LocationRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    val unit: String = locationRepository.getUnitForMeasurement()
    val weatherLiveData = MutableLiveData<Resource<ArrayList<WeatherResponse>>>()
    suspend fun fetchLocations() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    weatherLiveData.postValue(Resource.loading())
                    val latLngs = locationRepository.getAllLatLngs()
                    val weatherResponses = arrayListOf<WeatherResponse>()
                    latLngs?.forEach {
                        val response = locationRepository.getWeather(it.lat, it.lng)
                        if (response.isSuccessful && response.body() != null) {
                            val weatherResponse = response.body()!!
                            weatherResponse.latLngs = LatLng(it.lat?:0.0,it.lng?:0.0)
                            weatherResponses.add(weatherResponse)
                        }
                    }
                    weatherLiveData.postValue(
                        if (weatherResponses.isNullOrEmpty()) Resource.error()
                        else Resource.success(weatherResponses)
                    )
                }.onFailure {
                    weatherLiveData.postValue(Resource.error(it.message))
                }
            }
        }
    }

    suspend fun deleteWeatherInfo(latLng: LatLng?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    latLng?.let {
                        locationRepository.deleteWeatherInfo(latLng)
                    }
                }
            }
        }
    }
}