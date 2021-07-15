package com.ankuradlakha.weather.ui

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ankuradlakha.weather.R
import com.ankuradlakha.weather.data.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_location_search.*

@AndroidEntryPoint
class LocationSearchFragment : Fragment(), OnMapReadyCallback {
    private val viewModel by viewModels<LocationSearchViewModel>()
    private lateinit var googleMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClose()
        initMap()
        initSaveLocation()
    }

    private fun initClose() {
        ic_close.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initSaveLocation() {
        save_location.isEnabled = false
        save_location.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.saveLatLng().observe(viewLifecycleOwner, {
                    when (it.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            setFragmentResult(HomeFragment.requestLocationSelection, Bundle.EMPTY)
                            findNavController().navigateUp()
                        }
                        Status.ERROR -> {
                        }
                    }
                })
            }
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view)
                as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        initMarkerEvent()
    }

    private fun initMarkerEvent() {
        googleMap.setOnMapClickListener {
            addMarker(it)
        }
    }

    private fun addMarker(it: LatLng) {
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(it))
        viewModel.selectedLatLng = it
        save_location.isEnabled = true
    }
}