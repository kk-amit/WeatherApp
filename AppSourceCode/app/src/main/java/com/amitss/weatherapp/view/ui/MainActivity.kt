package com.amitss.weatherapp.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.amitss.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * Home screen activity for application.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d(getString(R.string.str_on_create))
        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Timber.d(getString(R.string.str_on_create_options_menu))
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.app_bar_search)

        return true
    }


}
