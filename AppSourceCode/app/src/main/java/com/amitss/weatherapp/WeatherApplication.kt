package com.amitss.weatherapp

import android.app.Application
import android.content.Context
import timber.log.Timber

class WeatherApplication : Application() {

    companion object {
        lateinit var mInstance: WeatherApplication
        var mContext: Context? = null

    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        mContext = applicationContext

        // setup timber debug tree for debug build
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}