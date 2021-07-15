package com.ankuradlakha.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankuradlakha.weather.data.Resource
import com.ankuradlakha.weather.data.repositories.LocationRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    var selectedLatLng: LatLng? = null
    suspend fun saveLatLng(): MutableLiveData<Resource<Boolean>> {
        val saveLocationLiveData = MutableLiveData<Resource<Boolean>>()
        saveLocationLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                runCatching {
                    locationRepository.saveLatLng(
                        selectedLatLng?.latitude ?: 0.0,
                        selectedLatLng?.longitude ?: 0.0
                    )
                    saveLocationLiveData.postValue(Resource.success(true))
                }.onFailure { saveLocationLiveData.postValue(Resource.error()) }
            }
        }
        return saveLocationLiveData
    }
}