package com.amitss.weatherapp.service.repository

import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.service.model.CityWeatherDetailModel
import com.amitss.weatherapp.view.util.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service to perform the API calls.
 */
interface WorldWeatherService {

    /**
     * API call return the World Weather City
     *
     * @param query - API Query parameter
     * @param numOfClients - API num of client requested
     * @param format - response form xml/json
     * @param key - API key
     *
     * @return Search API model.
     */
    @GET("/premium/v1/search.ashx")
    suspend fun getWorldWeatherCity(
        @Query("query") query: String,
        @Query("num_of_results") numOfClients: Int = NUM_OF_CLIENT,
        @Query("format") format: String = FORMAT,
        @Query("key") key: String = API_KEY
    ): Response<CitySearchModel>

    /**
     * API call return the Weather based on lat/long
     *
     * @param query - lat/long based query parameter.
     * @param date - weather date i.e. today
     * @param date - weather date i.e. today
     * @param format - response form xml/json
     * @param currentCondition true if currentCondition required otherwise false.
     * @param key - API key
     *
     *  @return CityModel model.
     */
    @GET("/premium/v1/weather.ashx")
    suspend fun getCityWeatherDetails(
        @Query("q") query: String,
        @Query("date") date: String = TODAY,
        @Query("format") format: String = FORMAT,
        @Query("cc") currentCondition: String = CURRENT_CONDITION,
        @Query("key") key: String = API_KEY
    ): Response<CityWeatherDetailModel>
}