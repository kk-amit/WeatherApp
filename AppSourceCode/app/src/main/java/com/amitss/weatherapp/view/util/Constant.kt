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
/**
 * World weather API client count
 */
const val NUM_OF_CLIENT = 10
/**
 * World weather API response format
 */
const val FORMAT = "json"
/**
 * World weather API base URL
 */
const val BASE_URL = "https://api.worldweatheronline.com/"
/**
 * Retrofit CONNECTION TIMEOUT
 */
const val HTTP_CONNECTION_TIMEOUT: Long = 120
/**
 * Retrofit Read TIMEOUT
 */
const val HTTP_READ_TIMEOUT: Long = 120
/**
 * Retrofit write TIMEOUT
 */
const val HTTP_WRITE_TIMEOUT: Long = 90