package com.amitss.weatherapp.service.repository

import com.amitss.weatherapp.service.model.CitySearchModel
import com.amitss.weatherapp.view.util.API_KEY
import com.amitss.weatherapp.view.util.FORMAT
import com.amitss.weatherapp.view.util.NUM_OF_CLIENT
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
}