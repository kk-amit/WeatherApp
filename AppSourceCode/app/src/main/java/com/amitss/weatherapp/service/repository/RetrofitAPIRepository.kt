package com.amitss.weatherapp.service.repository

import com.amitss.weatherapp.view.util.BASE_URL
import com.amitss.weatherapp.view.util.HTTP_CONNECTION_TIMEOUT
import com.amitss.weatherapp.view.util.HTTP_READ_TIMEOUT
import com.amitss.weatherapp.view.util.HTTP_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit API repository service instance for perform the retrofit API calls.
 */
object RetrofitAPIRepository {

    /**
     * Perform the World Weather Service api call
     *
     * @return WorldWeatherService - retrofit service reference.
     */
    fun makeRetrofitService(): WorldWeatherService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(makeOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(WorldWeatherService::class.java)
    }

    /**
     * Initialize the okhttp client with retrofit
     *
     * @return OkHttpClient reference.
     */
    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(makeLoggingInterceptor())
            .connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     *  Enable okhttp logging with retrofit calls.
     *
     *  @return HttpLoggingInterceptor An OkHttp interceptor which logs request and response information.
     */
    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            HttpLoggingInterceptor.Level.BODY
        return logging
    }
}