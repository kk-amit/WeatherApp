package com.amitss.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.CityResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * WeatherAppViewModel used as a communication layer App ui activity.
 */
class WeatherAppViewModel(application: Application) : AndroidViewModel(application) {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private lateinit var fetchDBLiveData: MutableLiveData<Any>

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
            val cityResponse: CityResponse<Any>? = CityResponse()

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

}