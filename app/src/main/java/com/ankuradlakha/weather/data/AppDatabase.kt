package com.ankuradlakha.weather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ankuradlakha.weather.data.daos.LocationDao
import com.ankuradlakha.weather.data.models.LatLngWrapper

@Database(
    entities = [LatLngWrapper::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}