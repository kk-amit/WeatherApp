package com.amitss.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.service.model.Response
import com.amitss.weatherapp.service.repository.RetrofitAPIRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * WeatherAppViewModel used as a communication layer App ui activity.
 */
class WeatherAppViewModel(application: Application) : AndroidViewModel(application) {

    // Kotlin coroutine properties.
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /**
     * Live data reference for DB operation.
     */
    private lateinit var fetchDBLiveData: MutableLiveData<Any>

    /**
     * Live data reference for API calls.
     */
    private lateinit var fetchAPILiveData: MutableLiveData<Any>

    /**
     * Fetch the city list from city_table
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun fetchDBCityList(): LiveData<Any> {
        fetchDBLiveData = MutableLiveData()
        scope.launch {

            val appDataBase: AppDatabase = AppDatabase.getInstance(getApplication())
            val cityData: List<CityEntity>? = appDataBase.cityDao()?.getAll()
            val cityResponse: Response<Any>? = Response()

            when {
                cityData == null -> {
                    cityResponse?.value = Exception()
                }
                cityData.isEmpty() -> {
                    cityResponse?.value = Exception()
                }
                else -> {
                    cityResponse?.value = cityData

                }
            }
            fetchDBLiveData.postValue(cityResponse)
        }
        return fetchDBLiveData
    }


    /**
     * Fetching the city search API data
     */
    fun fetAPICityList(str: String?): LiveData<Any> {
        fetchAPILiveData = MutableLiveData()
        scope.launch {
            val cityList: retrofit2.Response<CitySearchModel> =
                RetrofitAPIRepository.makeRetrofitService().getWorldWeatherCity("$str%")
            val cityResponse: Response<Any>? = Response()
            if (cityList.isSuccessful) {
                Timber.d(cityList.body().toString())
                val searchAPIModel = cityList.body() as CitySearchModel
                if (searchAPIModel.search_api != null) {
                    cityResponse?.value = searchAPIModel
                } else {
                    cityResponse?.value = Exception(cityList.body().toString())
                }
            } else {
                Timber.e(cityList.errorBody().toString())
                cityResponse?.value = Exception(cityList.errorBody().toString())
            }
            fetchAPILiveData.postValue(cityResponse)
        }

        return fetchAPILiveData
    }

}