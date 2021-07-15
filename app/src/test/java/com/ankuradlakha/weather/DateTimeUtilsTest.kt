package com.ankuradlakha.weather

import com.ankuradlakha.weather.utils.DateTimeUtils
import org.junit.Assert
import org.junit.Test

class DateTimeUtilsTest {
    @Test
    fun get_formatted_time_for_sun_timings_test_is_correct() {
        val formattedTime = DateTimeUtils.getFormattedTimeForSunTimings(1626333778406)
        Assert.assertEquals("12:52:58 PM",formattedTime)
    }
}