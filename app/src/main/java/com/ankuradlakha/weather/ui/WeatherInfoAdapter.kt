package com.ankuradlakha.weather.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankuradlakha.weather.R
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.databinding.ItemWeatherInfoBinding
import com.ankuradlakha.weather.utils.DateTimeUtils
import com.ankuradlakha.weather.utils.UnitOfMeasurement
import com.bumptech.glide.Glide
import java.util.*

class WeatherInfoAdapter(private val unitOfMeasurement: UnitOfMeasurement?) :
    RecyclerView.Adapter<WeatherInfoAdapter.ViewHolder>() {
    var weatherInfo = arrayListOf<WeatherResponse>()

    inner class ViewHolder(private val binding: ItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            weatherInfo[adapterPosition].let { weather ->
                binding.weather = weather
                weather.weather?.get(0)?.icon?.let {
                    Glide.with(binding.icWeather.context)
                        .load("https://openweathermap.org/img/wn/".plus(it).plus("@2x.png"))
                        .into(binding.icWeather)
                }
                weather.main?.tempMin?.let {
                    binding.minimumTemperature.text = String.format(
                        Locale.getDefault(),
                        binding.root.context.getString(R.string.temperature_format),
                        it.toString(),
                        unitOfMeasurement?.temperature ?: ""
                    )
                }
                weather.main?.tempMax?.let {
                    binding.maximumTemperature.text = String.format(
                        Locale.getDefault(),
                        binding.root.context.getString(R.string.temperature_format),
                        it.toString(),
                        unitOfMeasurement?.temperature ?: ""
                    )
                }

                binding.minimumTemperature.text =
                    binding.minimumTemperature.text.toString()
                        .plus(unitOfMeasurement?.temperature ?: "")
                binding.maximumTemperature.text =
                    binding.maximumTemperature.text.toString()
                        .plus(unitOfMeasurement?.temperature ?: "")
                weather.sys?.let { sys ->
                    binding.sunrise.text = DateTimeUtils.getFormattedTimeForSunTimings(
                        sys.sunrise ?: 0
                    )
                    binding.sunset.text = DateTimeUtils.getFormattedTimeForSunTimings(
                        sys.sunset ?: 0
                    )
                }
                binding.root.setOnClickListener {
                    onItemClicked?.invoke(weather)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemWeatherInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = weatherInfo.size
    fun setItems(weatherInfoList: ArrayList<WeatherResponse>?) {
        val diffUtilCallback = DiffUtils(weatherInfoList ?: arrayListOf(), weatherInfo)
        val result = DiffUtil.calculateDiff(diffUtilCallback)
        weatherInfo.clear()
        weatherInfo.addAll(weatherInfoList ?: arrayListOf())
        result.dispatchUpdatesTo(this)
    }

    fun removeItem(adapterPosition: Int) {
        weatherInfo.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    fun clearItems() {
        weatherInfo.clear()
        notifyDataSetChanged()
    }

    var onItemClicked: ((WeatherResponse) -> Unit)? = null

    inner class DiffUtils(
        private val oldList: ArrayList<WeatherResponse>,
        private val newList: ArrayList<WeatherResponse>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].coord?.latitude == newList[newItemPosition].coord?.latitude
                    && oldList[oldItemPosition].coord?.longitude == newList[newItemPosition].coord?.longitude
        }
    }
}