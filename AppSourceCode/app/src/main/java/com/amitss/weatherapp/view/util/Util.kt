package com.amitss.weatherapp.view.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.amitss.weatherapp.R
import com.amitss.weatherapp.WeatherApplication
import com.amitss.weatherapp.service.model.*

fun initModel(): CitySearchModel {
    val weatherUrl = WeatherUrl("")
    val region = Region(WeatherApplication.mInstance.getString(R.string.str_reason_not_found))
    val country =
        Country(WeatherApplication.mInstance.getString(R.string.str_country_not_found))
    val areaName = AreaName(WeatherApplication.mInstance.getString(R.string.str_city_na))

    val result = Result(
        arrayListOf(areaName), arrayListOf(country),
        arrayListOf(region), 0.0, 0.0, 0,
        arrayListOf(weatherUrl)
    )
    val searchAPI = SearchAPI(arrayListOf(result))
    return CitySearchModel(searchAPI)
}

/**
 * Check network connect
 * @return true if network is available otherwise false
 */
@Suppress("DEPRECATION")
fun isInternetAvailable(application: WeatherApplication): Boolean {
    var result = false
    val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        connectivityManager?.run {
            connectivityManager.activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    result = true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    result = true
                }
            }
        }
    }
    return result
}