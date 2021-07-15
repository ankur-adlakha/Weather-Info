package com.ankuradlakha.weather.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.ankuradlakha.weather.R
import kotlinx.android.synthetic.main.view_weather_info_header_body.view.*

class WeatherInfoHeaderBody @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.view_weather_info_header_body, this)
        readAttrs(attrs)
        setBackgroundResource(R.drawable.bg_weather_info_header_body)
        setPadding(16)
    }

    private fun readAttrs(attrs: AttributeSet?) {
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.WeatherInfoHeaderBody, 0, 0)
                .apply {
                    title.text = getString(R.styleable.WeatherInfoHeaderBody_header)
                    recycle()
                }
        }
    }

    fun setValue(value: String) {
        body.text = value
    }
}