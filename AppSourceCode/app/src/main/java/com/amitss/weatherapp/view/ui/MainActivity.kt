package com.amitss.weatherapp.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitss.weatherapp.R
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.exception.InternetNotAvailableException
import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.service.model.Response
import com.amitss.weatherapp.service.model.Result
import com.amitss.weatherapp.service.repository.RetrofitAPIRepository
import com.amitss.weatherapp.view.adapter.CityListAdapter
import com.amitss.weatherapp.view.adapter.CitySearchAdapter
import com.amitss.weatherapp.view.callback.IItemClickListener
import com.amitss.weatherapp.view.util.*
import com.amitss.weatherapp.viewmodel.WeatherAppViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.row_list_item.*
import timber.log.Timber

/**
 * Home screen activity for application.
 */
class MainActivity : BaseActivity(), IItemClickListener<CityEntity> {

    // MainActivity properties.
    private lateinit var weatherAppViewModel: WeatherAppViewModel
    private lateinit var cityAdapter: CitySearchAdapter
    private lateinit var citySearchModel: CitySearchModel
    private val context: Context = this
    private var cityListAdapter: CityListAdapter? = null
    private var cityList = ArrayList<CityEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d(getString(R.string.str_on_create))
        setSupportActionBar(toolbar)

        initView()

        // Empty model when Search Data not available
        citySearchModel = initModel(application = application)!!
        cityAdapter = CitySearchAdapter(context, citySearchModel)

        // View Model
        weatherAppViewModel =
            ViewModelProviders.of(context as FragmentActivity).get(WeatherAppViewModel::class.java)


        cityListAdapter = CityListAdapter(context, cityList)
        cityListAdapter?.setListener(this)
        viewCity.adapter = cityListAdapter

    }

    /**
     * Initialising the activity view.
     */
    private fun initView() {
        // City List Recycler-view and adapter Setting.
        val viewCity: RecyclerView = viewCity
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        viewCity.layoutManager = linearLayoutManager
        viewCity.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        Timber.d(getString(R.string.str_on_resume))
        getSavedCityList()
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.d(getString(R.string.str_on_destroy))
        if (cityListAdapter != null) {
            cityListAdapter?.removeListener()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Timber.d(getString(R.string.str_on_create_options_menu))
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val menuItem: MenuItem? = menu.findItem(R.id.app_bar_search)

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

        searchAutoComplete.setOnItemClickListener { parent, view, position, id ->
            Timber.d("%s %s", getString(R.string.str_on_item_click_listener), position)

            searchView.setQuery("", false)
            searchView.clearFocus()
            searchView.isIconified = true

            val selectedCity = citySearchModel.search_api?.result?.get(position)
            selectedCity?.let { savedSelectedCity(it) }
        }

        return true
    }

    /**
     * Returned list of saved last ten city entry from Database.
     */
    private fun getSavedCityList() {
        Timber.d(getString(R.string.str_saved_city_list))

        weatherAppViewModel.fetchDBCityList(AppDatabase.getInstance(application))
            .observe(context as MainActivity, Observer {
                if (it == null || (it as Response<*>).value is Exception) {
                    Timber.d(getString(R.string.str_prev_search_error_title))
                    updateSearchListNotFoundError()
                } else {
                    Timber.d((it as Response<*>).value.toString())
                    updateSearchListView(false)
                    cityList.clear()
                    cityList.addAll(it.value as ArrayList<CityEntity>)
                    cityListAdapter?.setCityValue(cityList)
                    cityListAdapter?.notifyDataSetChanged()
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
        weatherAppViewModel.fetAPICityList(RetrofitAPIRepository.makeRetrofitService(), city)
            .observe(context as MainActivity, Observer {

                citySearchModel = if (it == null || (it as Response<*>).value == null) {
                    Timber.d("null")
                    initModel(application = application)!!
                } else if (it.value is InternetNotAvailableException) {
                    Timber.d(it.value.toString())
                    handleNoInternet(context)
                    return@Observer
                } else if (it.value is Exception) {
                    Timber.d(it.value.toString())
                    initModel(application = application)!!
                } else {
                    Timber.d(it.value.toString())
                    it.value as CitySearchModel
                }
                cityAdapter.setSearchModel(citySearchModel)
                cityAdapter.notifyDataSetChanged()
            })

    }

    /**
     * Perform DB operation and save the City in DB
     *
     * @param searchResult - Search Result
     **/
    private fun savedSelectedCity(searchResult: Result) {
        Timber.d(getString(R.string.str_save_city))

        weatherAppViewModel.saveCity(AppDatabase.getInstance(application), searchResult)
            .observe(context as MainActivity, Observer {
                if (it == null || (it as Response<*>).value == null || (it).value is Exception) {
                    return@Observer
                } else {
                    val response = it
                    val city = response.value as CityEntity
                    openWeatherDetailScreen(city.latitude, city.longitude, city.areaName)
                }
            })
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

    override fun onItemClick(item: CityEntity) {
        Timber.d(item.toString())
        // Start another activity for selected position
        openWeatherDetailScreen(item.latitude, item.longitude, item.areaName)
    }

    /**
     *  Open the Weather Detail Screen
     */
    private fun openWeatherDetailScreen(latitude: Double?, longitude: Double?, areaName: String?) {
        Timber.d(latitude.toString())
        Timber.d(latitude.toString())
        Timber.d(areaName)

        val intent = Intent(context, WeatherDetailActivity::class.java)
        intent.putExtra(LATITUDE, latitude)
        intent.putExtra(LONGITUDE, longitude)
        intent.putExtra(CITY, areaName)
        startActivity(intent)
    }
}
