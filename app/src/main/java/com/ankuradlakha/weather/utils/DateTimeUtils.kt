package com.ankuradlakha.weather.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        fun getFormattedTimeForSunTimings(timeInMillis: Long): String {
            return SimpleDateFormat("hh:mm:ss aa", Locale.getDefault()).format(Date(timeInMillis))
        }

        fun getCurrentDateAndTime(): String {
            return SimpleDateFormat("dd MMM yyyy',' hh:mm aa", Locale.getDefault()).format(Date())
        }
    }
}