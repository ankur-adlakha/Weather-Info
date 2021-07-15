package com.ankuradlakha.weather.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ankuradlakha.weather.data.models.LatLngWrapper

@Dao
interface LocationDao {
    @Insert
    fun saveLatLng(latLng: LatLngWrapper)

    @Query("Select * from LatLngWrapper")
    fun getAllLatLngs(): List<LatLngWrapper>?

    @Query("Delete from LatLngWrapper where latitude=:lat and longitude=:lng")
    fun deleteLatLng(lat: Double, lng: Double)

    @Query("Delete from LatLngWrapper")
    fun deleteAllLatLngs()
}