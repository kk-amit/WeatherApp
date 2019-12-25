package com.amitss.weatherapp.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amitss.weatherapp.R
import com.amitss.weatherapp.service.model.CityWeatherDetailModel
import com.amitss.weatherapp.service.model.Response
import com.amitss.weatherapp.view.util.*
import com.amitss.weatherapp.viewmodel.WeatherAppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_weather_detail.*
import timber.log.Timber

class WeatherDetailActivity : BaseActivity() {

    private lateinit var weatherAppViewModel: WeatherAppViewModel
    private val context: Context = this
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var city: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d(getString(R.string.str_on_create))
        setContentView(R.layout.activity_weather_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // View Model
        weatherAppViewModel =
            ViewModelProviders.of(context as FragmentActivity).get(WeatherAppViewModel::class.java)

        latitude = intent.getDoubleExtra(LATITUDE, DEF_CITY_LAT)
        longitude = intent.getDoubleExtra(LONGITUDE, DEF_CITY_LONG)
        city = if (intent.getStringExtra(CITY) != null) intent.getStringExtra(CITY) else DEF_CITY

        fetchCityWeatherDetail()
    }

    /**
     *
     */
    private fun fetchCityWeatherDetail() {
        val latLongQuery = "$latitude,$longitude"
        weatherAppViewModel.getCityWeatherDetail(latLongQuery)
            .observe(this as FragmentActivity, Observer {
                if (it == null || (it as Response<*>).value == null || (it).value is Exception) {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                } else if (it.value is Boolean) {
                    progressBar.visibility = View.VISIBLE
                    tvError.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                    tvError.visibility = View.GONE
                    updateWeatherDetailUI(it.value as CityWeatherDetailModel)
                }

            })
    }

    /**
     * Update the UI for Weather Detail screen
     */
    @SuppressLint("SetTextI18n")
    fun updateWeatherDetailUI(cityModel: CityWeatherDetailModel) {
        val tempInCentigrade = cityModel.data?.current_condition?.get(0)?.temp_C
        val weatherIconURL =
            cityModel.data?.current_condition?.get(0)?.weatherIconUrl?.get(0)?.value
        val observationTime = cityModel.data?.current_condition?.get(0)?.observation_time
        val weatherDesc = cityModel.data?.current_condition?.get(0)?.weatherDesc?.get(0)?.value
        val humidity = cityModel.data?.current_condition?.get(0)?.humidity
        val visibility = cityModel.data?.current_condition?.get(0)?.visibility

        tvCity.text = city
        tvTempC.text =
            "${tempInCentigrade}${getString(R.string.str_c_ic)} ${getString(R.string.str_c)}"

        loadImage(imgWeatherIcon, weatherIconURL)

        tvTime.text = "${getString(R.string.str_observation_time)} $observationTime"
        tvWeatherDetail.text = "$weatherDesc"

        tvHumidity.text = "${getString(R.string.str_humidity)} $humidity"
        tvVisibility.text =
            "${getString(R.string.str_visibility)} $visibility ${getString(R.string.str_km)}"

        group.visibility = View.VISIBLE

    }
}