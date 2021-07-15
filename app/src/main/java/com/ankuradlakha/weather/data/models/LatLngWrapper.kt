package com.ankuradlakha.weather.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatLngWrapper(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "latitude")
    var lat: Double?,
    @ColumnInfo(name = "longitude")
    var lng: Double?
) {
    constructor(lat: Double?, lng: Double?) : this(null, lat, lng)
}