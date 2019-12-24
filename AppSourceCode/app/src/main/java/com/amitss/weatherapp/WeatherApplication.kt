package com.amitss.weatherapp

import android.app.Application
import android.content.Context
import android.util.Base64
import timber.log.Timber

/**
 * Base class for maintaining global application state.
 */
class WeatherApplication : Application() {

    init {
        System.loadLibrary("keys")
    }

    /**
     * Get encoded API key from native C file.
     */
    private external fun getAPIKey(): String

    /**
     * Instance for WeatherApplication
     */
    companion object {
        lateinit var mInstance: WeatherApplication
        var mContext: Context? = null

    }

    override fun onCreate() {
        super.onCreate()
        Timber.d(getString(R.string.str_on_create))
        mInstance = this
        mContext = applicationContext

        // setup timber debug tree for debug build
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d(getWeatherAppAPIKey())
    }

    /**
     * Global access for API KEY
     */
    fun getWeatherAppAPIKey() = String(Base64.decode(getAPIKey(), Base64.DEFAULT))
}