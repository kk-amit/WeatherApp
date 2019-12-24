package com.amitss.weatherapp.view.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amitss.weatherapp.R
import com.amitss.weatherapp.service.exception.InternetNotAvailableException
import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.service.model.Response
import com.amitss.weatherapp.view.adapter.CitySearchAdapter
import com.amitss.weatherapp.view.util.QUERY_SIZE
import com.amitss.weatherapp.view.util.initModel
import com.amitss.weatherapp.viewmodel.WeatherAppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_list_item.*
import timber.log.Timber

/**
 * Home screen activity for application.
 */
class MainActivity : BaseActivity() {

    private lateinit var weatherAppViewModel: WeatherAppViewModel
    private val context: Context = this
    private lateinit var cityAdapter: CitySearchAdapter
    private lateinit var citySearchModel: CitySearchModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d(getString(R.string.str_on_create))
        setSupportActionBar(toolbar)

        // Empty model when Search Data not availabale
        citySearchModel = initModel()
        cityAdapter = CitySearchAdapter(context, citySearchModel)

        // View Model
        weatherAppViewModel =
            ViewModelProviders.of(context as FragmentActivity).get(WeatherAppViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Timber.d(getString(R.string.str_on_create_options_menu))
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.app_bar_search)

        // Menu Search View and auto complete
        val searchView = menuItem?.actionView as SearchView
        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(R.id.search_src_text) as SearchView.SearchAutoComplete
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.background_light)
        searchAutoComplete.setAdapter(cityAdapter)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(str: String?): Boolean {
                Timber.d("%s %s", getString(R.string.str_on_query_text_submit), str)
                searchView.isIconified = true
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(str: String?): Boolean {
                if (str!!.length >= QUERY_SIZE) {
                    Timber.d("%s %s", getString(R.string.str_on_query_text_change), str)
                    getAPICityList(str)
                }
                return false
            }
        })

        return true
    }

    override fun onResume() {
        super.onResume()
        Timber.d(getString(R.string.str_on_resume))
        getSavedCityList()
    }

    /**
     * Returned list of saved last ten city entry from Database.
     */
    private fun getSavedCityList() {
        Timber.d(getString(R.string.str_saved_city_list))

        weatherAppViewModel.fetchDBCityList().observe(context as MainActivity, Observer {
            if (it == null || (it as Response<*>).value is Exception) {
                Timber.d(getString(R.string.str_prev_search_error_title))
                updateSearchListNotFoundError()
            } else {
                Timber.d((it as Response<*>).value.toString())
                updateSearchListView(false)
            }
        })

    }

    /**
     * Perform city search API call
     *
     * @param city - City List
     */
    private fun getAPICityList(city: String) {
        Timber.d(getString(R.string.str_api_city_list))
        weatherAppViewModel.fetAPICityList(city).observe(context as MainActivity, Observer {

            if (it == null || (it as Response<*>).value == null) {
                Timber.d("null")
                citySearchModel = initModel()
            } else if ((it as Response<*>).value is InternetNotAvailableException) {
                Timber.d((it as Response<*>).value.toString())
                handleNoInternet()
                return@Observer
            } else if ((it as Response<*>).value is Exception) {
                Timber.d((it as Response<*>).value.toString())
                citySearchModel = initModel()
            } else {
                Timber.d((it as Response<*>).value.toString())
                citySearchModel = (it as Response<*>).value as CitySearchModel
            }
            cityAdapter.setSearchModel(citySearchModel)
            cityAdapter.notifyDataSetChanged()
        })

    }

    /**
     * Handling the No internet.
     */
    private fun handleNoInternet() {
        showAlertDialog(
            context,
            getString(R.string.str_no_internet),
            getString(R.string.str_no_internet_message),
            getString(R.string.str_ok),
            R.drawable.ic_error
        )
    }

    /**
     * Update Error screen while not able to find the saved city list
     */
    private fun updateSearchListNotFoundError() {
        updateSearchListView(true)
        ivIcon.setBackgroundResource(R.drawable.ic_info)
        ivIcon.invalidate()
        tvName.text = getString(R.string.str_prev_search_error_title)
        tvMessage.text = getString(R.string.str_prev_search_error_message)
    }

    /**
     * Show/hide error view
     */
    private fun updateSearchListView(isError: Boolean) {
        infoView.visibility = if (isError) {
            View.VISIBLE
        } else {
            View.GONE
        }
        viewCity.visibility = if (isError) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }


}
