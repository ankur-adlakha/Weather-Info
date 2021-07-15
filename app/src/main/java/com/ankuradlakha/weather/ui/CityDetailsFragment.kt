package com.ankuradlakha.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ankuradlakha.weather.R
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.utils.DateTimeUtils
import com.ankuradlakha.weather.utils.UnitOfMeasurement
import com.ankuradlakha.weather.utils.argWeather
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_city_details.*

@AndroidEntryPoint
class CityDetailsFragment : Fragment() {
    private val viewModel by viewModels<CityDetailsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(argWeather, "")?.let {
            viewModel.weather = Gson().fromJson(it, WeatherResponse::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTemperature()
        initHumidity()
        initRain()
        initWindDetails()
        initCityName()
        initDayAndDate()
        initTemperatureUnit()
        initWeatherImage()
    }

    private fun initWeatherImage() {
        viewModel.weather?.weather?.get(0)?.icon?.let {
            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/".plus(it).plus("@4x.png"))
                .into(ic_weather)
        }
    }

    private fun initTemperatureUnit() {
        temperature_unit.text =
            UnitOfMeasurement.values().find { it.unit == viewModel.unit }?.temperature
    }

    private fun initDayAndDate() {
        day_date.text = DateTimeUtils.getCurrentDateAndTime()
    }

    private fun initCityName() {
        viewModel.weather?.name?.let {
            city_name.text = it
        }
    }

    private fun initWindDetails() {
        viewModel.weather?.wind?.let {
            wind_direction_info.setValue(it.deg?.toString() ?: "")
            wind_speed_info.setValue(it.speed?.toString() ?: "")
        }
    }

    private fun initRain() {
        if (viewModel.weather?.rain == null) {
            rain_info.visibility = View.GONE
        } else {
            viewModel.weather?.rain?.let {
                rain_info.setValue(it.oneHour ?: "")
            }
        }
    }

    private fun initHumidity() {
        viewModel.weather?.main?.humidity?.let {
            humidity_info.setValue(it.toString())
        }
    }

    private fun initTemperature() {
        viewModel.weather?.main?.temp?.let {
            temperature.text = it.toString()
        }
    }
}