package com.ankuradlakha.weather.ui

import androidx.lifecycle.ViewModel
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.data.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityDetailsViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    var unit: String? = null
    var weather: WeatherResponse? = null

    init {
        unit = getUnitForMeasurement()
    }

    private fun getUnitForMeasurement() = locationRepository.getUnitForMeasurement()
}