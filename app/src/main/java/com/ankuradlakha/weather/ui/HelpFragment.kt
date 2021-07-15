package com.ankuradlakha.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ankuradlakha.weather.R
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initHelp()
    }

    private fun initHelp() {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initToolbar() {
        webview_help.settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            displayZoomControls = true
        }
        webview_help.loadUrl("https://sites.google.com/view/weather-help/home")
    }
}