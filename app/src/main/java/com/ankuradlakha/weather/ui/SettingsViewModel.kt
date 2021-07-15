package com.ankuradlakha.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankuradlakha.weather.data.repositories.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
        fun saveUnitOfMeasurement(unitOfMeasurement:String){
            locationRepository.saveUnitOfMeasurement(unitOfMeasurement)
        }

    fun getUnitOfMeasurement() = locationRepository.getUnitForMeasurement()
    suspend fun deleteBookmarkedCities(): MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        viewModelScope.launch{
            withContext(Dispatchers.IO){
                runCatching {
                    locationRepository.deleteAllWeatherInfo()
                    liveData.postValue(true)
                }
            }
        }
        return liveData
    }
}