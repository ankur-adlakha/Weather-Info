package com.ankuradlakha.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ankuradlakha.weather.MainViewModel
import com.ankuradlakha.weather.R
import com.ankuradlakha.weather.utils.UnitOfMeasurement
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet_settings.*

@AndroidEntryPoint
class SettingsBottomSheet : BottomSheetDialogFragment() {
    private val viewModel by viewModels<SettingsViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUnitOfMeasurement()
        initSaveChanges()
        initClearBookmarkedCities()
    }

    private fun initClearBookmarkedCities() {
        clear_bookmarked_cities.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.delete_cities))
                .setMessage(getString(R.string.delete_all_cities_message))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    deleteCities()
                }
                .setNegativeButton(getString(R.string.no), null).show()
        }
    }

    private fun deleteCities() {
        lifecycleScope.launchWhenCreated {
            viewModel.deleteBookmarkedCities().observe(viewLifecycleOwner, {
                mainViewModel.refreshCitiesListLiveData.value = true
                dismiss()
            })
        }
    }

    private fun initUnitOfMeasurement() {
        rg_unit_of_measurement.check(
            when (viewModel.getUnitOfMeasurement()) {
                UnitOfMeasurement.IMPERIAL.unit -> {
                    R.id.unit_imperial
                }
                UnitOfMeasurement.STANDARD.unit -> {
                    R.id.unit_standard
                }
                UnitOfMeasurement.METRIC.unit -> {
                    R.id.unit_metric
                }
                else -> R.id.unit_metric
            }
        )
    }

    private fun initSaveChanges() {
        save_changes.setOnClickListener {
            viewModel.saveUnitOfMeasurement(
                when (rg_unit_of_measurement.checkedRadioButtonId) {
                    R.id.unit_imperial -> {
                        UnitOfMeasurement.IMPERIAL.unit
                    }
                    R.id.unit_metric -> {
                        UnitOfMeasurement.METRIC.unit
                    }
                    R.id.unit_standard -> {
                        UnitOfMeasurement.STANDARD.unit
                    }
                    else -> {
                        UnitOfMeasurement.STANDARD.unit
                    }
                }
            )
            mainViewModel.refreshCitiesListLiveData.value = true
            dismiss()
        }
    }
}