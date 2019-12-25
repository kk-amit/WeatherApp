package com.amitss.weatherapp.view.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.ImageView
import com.amitss.weatherapp.R
import com.amitss.weatherapp.WeatherApplication
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.model.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Initialise the empty City Model
 */
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
 * Convert the API result in City Entity
 */
fun getCityEntity(searchAPI: Result): CityEntity {
    return CityEntity(
        searchAPI.areaName?.get(0)?.value,
        searchAPI.country?.get(0)?.value,
        searchAPI.region?.get(0)?.value,
        searchAPI.latitude,
        searchAPI.longitude,
        searchAPI.population,
        searchAPI.weatherUrl?.get(0)?.value
    )
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

/**
 * Load the image from URL into Imageview
 *
 * @param imageView - view reference
 * @param imageUrl - URL to load image
 */
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context).setDefaultRequestOptions(RequestOptions())
        .load(imageUrl).placeholder(R.drawable.ic_cloud).into(imageView)
}
