package com.ankuradlakha.weather.data

import android.content.Context
import android.content.SharedPreferences
import com.ankuradlakha.weather.utils.UnitOfMeasurement
import javax.inject.Inject

class AppCache @Inject constructor(context: Context) {
    companion object {
        const val prefUnitOfMeasurement = "unit_of_measurement"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("", Context.MODE_PRIVATE)

    fun saveUnitOfMeasurement(unit: String) =
        sharedPreferences.edit().putString(prefUnitOfMeasurement, unit).commit()

    fun getUnitOfMeasurement() =
        sharedPreferences.getString(prefUnitOfMeasurement, UnitOfMeasurement.METRIC.unit)
            ?: UnitOfMeasurement.METRIC.unit
}