package com.amitss.weatherapp.view.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.amitss.weatherapp.R
import timber.log.Timber

/**
 * Abstract Base Activity for entire application.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.d(getString(R.string.str_on_create))
    }

    override fun onRestart() {
        super.onRestart()
        Timber.d(getString(R.string.str_on_restart))
    }

    override fun onStart() {
        super.onStart()
        Timber.d(getString(R.string.str_on_start))
    }

    override fun onResume() {
        super.onResume()
        Timber.d(getString(R.string.str_on_resume))
    }

    override fun onPause() {
        super.onPause()
        Timber.d(getString(R.string.str_on_pause))
    }

    override fun onStop() {
        super.onStop()
        Timber.d(getString(R.string.str_on_stop))
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d(getString(R.string.str_on_destroy))
    }
}