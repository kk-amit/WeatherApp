package com.amitss.weatherapp.view.util

import com.amitss.weatherapp.WeatherApplication

/**
 * 3 second delay for Splash scree.
 */
const val SPLASH_DELAY: Long = 3000 // 3 sec
/**
 * App DB
 */
const val APP_DB_NAME = "weatherapp.db"
/**
 * API key loaded from native c file
 */
var API_KEY = WeatherApplication.mInstance.getWeatherAppAPIKey()
