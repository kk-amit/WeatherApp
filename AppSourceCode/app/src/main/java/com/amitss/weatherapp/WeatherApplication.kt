package com.amitss.weatherapp

import android.app.Application
import android.content.Context
import timber.log.Timber

/**
 * Base class for maintaining global application state.
 */
class WeatherApplication : Application() {

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
    }
}