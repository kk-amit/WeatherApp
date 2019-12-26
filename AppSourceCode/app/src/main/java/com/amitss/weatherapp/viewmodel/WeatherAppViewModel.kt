package com.amitss.weatherapp.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.service.exception.InternetNotAvailableException
import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.service.model.CityWeatherDetailModel
import com.amitss.weatherapp.service.model.Response
import com.amitss.weatherapp.service.model.Result
import com.amitss.weatherapp.service.repository.WorldWeatherService
import com.amitss.weatherapp.view.util.getCityEntity
import com.amitss.weatherapp.view.util.initModel
import com.amitss.weatherapp.view.util.isInternetAvailable
import kotlinx.coroutines.*
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
     * Live data reference for fetch DB operation.
     */
    private lateinit var fetchDBLiveData: MutableLiveData<Any>

    /**
     * Live data reference for Save DB operation.
     */
    private lateinit var saveDBLiveData: MutableLiveData<Any>

    /**
     * Live data reference for fetch City API calls.
     */
    private lateinit var fetchCityAPILiveData: MutableLiveData<Any>

    /**
     * Live data reference for fetch weather Details API calls.
     */
    private lateinit var fetchWeatherDetailAPILiveData: MutableLiveData<Any>

    /**
     * Fetch the city list from city_table
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun fetchDBCityList(appDataBase: AppDatabase?): LiveData<Any> {
        fetchDBLiveData = MutableLiveData()
        val cityResponse: Response<Any>? = Response()
        if (appDataBase == null) {
            cityResponse?.value = Exception()
            fetchDBLiveData.postValue(cityResponse)
        } else {
            scope.launch {
                val cityData: List<CityEntity>? = appDataBase.cityDao()?.getAll()
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
        }
        return fetchDBLiveData
    }


    /**
     * Fetching the city search API data
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun fetAPICityList(worldWeatherService: WorldWeatherService?, str: String?): LiveData<Any> {
        fetchCityAPILiveData = MutableLiveData()

        val cityResponse: Response<Any>? = Response()
        when {
            worldWeatherService == null -> {
                cityResponse?.value = Exception()
                fetchCityAPILiveData.postValue(cityResponse)
            }
            isInternetAvailable(application = getApplication())!! -> {
                scope.launch {
                    val cityList: retrofit2.Response<CitySearchModel>? =
                        worldWeatherService.getWorldWeatherCity("$str%")

                    if (cityList?.isSuccessful!!) {
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
                    fetchCityAPILiveData.postValue(cityResponse)
                }
            }
            else -> {
                cityResponse?.value = InternetNotAvailableException("")
                fetchCityAPILiveData.postValue(cityResponse)
            }
        }
        return fetchCityAPILiveData
    }

    /**
     * Save the City in DB
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun saveCity(appDataBase: AppDatabase?, searchAPI: Result?): LiveData<Any> {
        saveDBLiveData = MutableLiveData()
        val saveResponse: Response<Any>? = Response()
        if (searchAPI == null) {
            saveResponse?.value = Exception()
            saveDBLiveData.postValue(saveResponse)
        } else if (appDataBase == null) {
            saveResponse?.value = Exception()
            saveDBLiveData.postValue(saveResponse)
        } else if (searchAPI == initModel(application = getApplication())?.search_api?.result?.get(0)) {
            saveResponse?.value = Exception()
            saveDBLiveData.postValue(saveResponse)
        } else {
            scope.launch {
                val cityEntity = getCityEntity(searchAPI)
                var cityData: CityEntity? = null
                try {
                    Timber.d(cityEntity.toString())
                    cityEntity?.let { appDataBase.cityDao()?.insert(it) }
                    cityData = appDataBase.cityDao()?.getLastCity()
                } catch (ex: SQLiteConstraintException) {
                    Timber.e(ex)
                    cityData = cityEntity
                } finally {
                    if (cityData != null) {
                        saveResponse?.value = cityData
                        saveDBLiveData.postValue(saveResponse)
                    }
                }
            }
        }
        return saveDBLiveData
    }

    /**
     * Fetching the Weather Details API data
     *
     * @return LiveData is a data holder class that can be observed within a given lifecycle.
     */
    fun getCityWeatherDetail(
        worldWeatherService: WorldWeatherService?,
        str: String?
    ): LiveData<Any> {
        fetchWeatherDetailAPILiveData = MutableLiveData()
        val apiResponse: Response<Any>? = Response()

        // perform the loading
        apiResponse?.value = true
        fetchWeatherDetailAPILiveData.postValue(apiResponse)

        if (worldWeatherService == null) {
            apiResponse?.value = Exception()
        } else if (isInternetAvailable(getApplication())!!) {
            scope.launch {

                val weatherDetail: retrofit2.Response<CityWeatherDetailModel> =
                    worldWeatherService.getCityWeatherDetails("$str")

                if (weatherDetail.isSuccessful) {
                    Timber.d(weatherDetail.body().toString())
                    val cityModel = weatherDetail.body() as CityWeatherDetailModel
                    if (cityModel.data != null) {
                        apiResponse?.value = cityModel
                    } else {
                        apiResponse?.value = Exception(weatherDetail.body().toString())
                    }
                } else {
                    Timber.e(weatherDetail.errorBody().toString())
                    apiResponse?.value = Exception(weatherDetail.errorBody().toString())
                }
                fetchWeatherDetailAPILiveData.postValue(apiResponse)
            }
        } else {
            apiResponse?.value = InternetNotAvailableException("")
            fetchWeatherDetailAPILiveData.postValue(apiResponse)
        }
        return fetchWeatherDetailAPILiveData
    }

    /**
     * Cancel the  coroutine request.
     */
    fun cancelAllRequests() = coroutineContext.cancel()

}