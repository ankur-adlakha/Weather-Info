package com.ankuradlakha.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ankuradlakha.weather.MainViewModel
import com.ankuradlakha.weather.R
import com.ankuradlakha.weather.data.Status
import com.ankuradlakha.weather.data.models.WeatherResponse
import com.ankuradlakha.weather.utils.UnitOfMeasurement
import com.ankuradlakha.weather.utils.argWeather
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    companion object {
        const val requestLocationSelection = "request_key_location"
    }

    private val viewModel by viewModels<HomeViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var adapter : WeatherInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = WeatherInfoAdapter(UnitOfMeasurement.values().find { it.unit == viewModel.unit })
        setFragmentResultListener(
            requestLocationSelection,
            listener = { requestKey: String, _: Bundle ->
                if (requestKey == requestLocationSelection) {
                    fetchWeatherInformation()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomBar()
        initLocationSearch()
        initWeatherInformation()
        fetchWeatherInformation()
        initSwipeToDelete()
        initRefreshCitiesList()
    }

    private fun initRefreshCitiesList() {
        mainViewModel.refreshCitiesListLiveData.observe(viewLifecycleOwner, {
            if (it) {
                adapter.clearItems()
                fetchWeatherInformation()
                mainViewModel.refreshCitiesListLiveData.value = false
            }
        })
    }

    private fun initSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.LEFT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeWeatherInfo(adapter.weatherInfo[viewHolder.adapterPosition])
                adapter.removeItem(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(view_weather_info)
    }

    private fun removeWeatherInfo(weatherResponse: WeatherResponse) {
        lifecycleScope.launchWhenCreated {
            viewModel.deleteWeatherInfo(weatherResponse.latLngs)
        }
    }

    private fun initLocationSearch() {
        fab_location_search.setOnClickListener {
            findNavController().navigate(R.id.navigate_to_fragment_location_search)
        }
    }

    private fun initBottomBar() {
        bottom_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_help -> {
                    findNavController().navigate(R.id.navigate_to_fragment_help)
                }
                R.id.menu_settings -> {
                    SettingsBottomSheet().show(
                        childFragmentManager,
                        SettingsBottomSheet::class.java.simpleName
                    )
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun fetchWeatherInformation() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchLocations()
        }
    }

    private fun initWeatherInformation() {
        view_weather_info.adapter = adapter
        adapter.onItemClicked = {
            findNavController().navigate(R.id.navigate_to_fragment_city_details, Bundle().apply {
                putString(argWeather, Gson().toJson(it))
            })
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    adapter.setItems(it.data)
                    if (it.data.isNullOrEmpty()) {
                        empty_screen_message.visibility = View.VISIBLE
                        down_arrow_animation.visibility = View.VISIBLE
                    } else {
                        empty_screen_message.visibility = View.GONE
                        down_arrow_animation.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    adapter.clearItems()
                    empty_screen_message.visibility = View.VISIBLE
                    down_arrow_animation.visibility = View.VISIBLE
                }
            }
        })
    }
}