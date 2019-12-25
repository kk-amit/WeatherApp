package com.amitss.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amitss.weatherapp.database.AppDatabase
import com.amitss.weatherapp.service.model.*
import com.amitss.weatherapp.service.repository.WorldWeatherService
import com.amitss.weatherapp.viewmodel.WeatherAppViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class WeatherAppViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private inline fun <reified T> mock() = Mockito.mock(T::class.java)
    private var application: WeatherApplication? = null
    private var weatherAppViewModel: WeatherAppViewModel? = null
    private var appDatabase: AppDatabase? = null
    private var worldWeatherService: WorldWeatherService? = null
    private var queryString: String? = null
    private var searchAPI: Result? = null

    @Before
    fun setup() {
        application = mock()
        weatherAppViewModel = WeatherAppViewModel(application!!)
    }

    @After
    fun tearDown() {
        weatherAppViewModel = null
    }

    @Test
    fun testFetchDBCityListNullVal() {
        weatherAppViewModel?.fetchDBCityList(appDatabase)?.observeForever(Observer {
            assert((it as Response<*>).value is Exception)
        })
    }

    @Test
    fun testFetchDBCityListVal() {
        appDatabase = mock()
        weatherAppViewModel?.fetchDBCityList(appDatabase)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testFetAPICityListServiceQueryNullVal() {
        weatherAppViewModel?.fetAPICityList(worldWeatherService, queryString)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testFetAPICityListServiceNullVal() {
        queryString = "test"
        weatherAppViewModel?.fetAPICityList(worldWeatherService, queryString)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testFetAPICityListQueryNullVal() {
        worldWeatherService = mock()
        queryString = null
        weatherAppViewModel?.fetAPICityList(worldWeatherService, queryString)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testFetAPICityListVal() {
        worldWeatherService = mock()
        queryString = "test"
        weatherAppViewModel?.fetAPICityList(worldWeatherService, queryString)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testSaveCityDBCityNullVal() {
        weatherAppViewModel?.saveCity(appDatabase, searchAPI)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testSaveCityDBNullVal() {
        searchAPI = initCityResult()
        weatherAppViewModel?.saveCity(appDatabase, initCityResult())?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testSaveCityNullVal() {
        appDatabase = mock()
        searchAPI = null
        weatherAppViewModel?.saveCity(appDatabase, searchAPI)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testSaveCityVal() {
        appDatabase = mock()
        searchAPI = initCityResult()
        weatherAppViewModel?.saveCity(appDatabase, searchAPI)?.observeForever {
            assert((it as Response<*>).value is Exception)
        }
    }

    @Test
    fun testGetWeatherCityDetailServiceQueryNullVal() {
        weatherAppViewModel?.getCityWeatherDetail(worldWeatherService, queryString)
            ?.observeForever {
                assert((it as Response<*>).value is Exception)
            }
    }

    @Test
    fun testGetWeatherCityDetailServiceNullVal() {
        queryString = "test"
        weatherAppViewModel?.getCityWeatherDetail(worldWeatherService, queryString)
            ?.observeForever {
                assert((it as Response<*>).value is Exception)
            }
    }

    @Test
    fun testGetWeatherCityDetailQueryNullVal() {
        worldWeatherService = mock()
        queryString = null
        weatherAppViewModel?.getCityWeatherDetail(worldWeatherService, queryString)
            ?.observeForever {
                assert((it as Response<*>).value is Exception)
            }
    }

    @Test
    fun testGetWeatherCityDetailVal() {
        worldWeatherService = mock()
        queryString = "test"
        weatherAppViewModel?.getCityWeatherDetail(worldWeatherService, queryString)
            ?.observeForever {
                assert((it as Response<*>).value is Exception)
            }
    }

    private fun initCityResult(): Result {
        val weatherUrl = WeatherUrl("")
        val region = Region("")
        val country =
            Country("")
        val areaName = AreaName("")
        return Result(
            arrayListOf(areaName), arrayListOf(country),
            arrayListOf(region), 0.0, 0.0, 0,
            arrayListOf(weatherUrl)
        )
    }


}