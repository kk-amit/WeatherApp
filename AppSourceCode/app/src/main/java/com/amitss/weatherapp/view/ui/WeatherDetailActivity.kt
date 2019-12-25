package com.amitss.weatherapp.view.ui

import android.os.Bundle
import com.amitss.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class WeatherDetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(getString(R.string.str_on_create))
        setContentView(R.layout.activity_weather_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}