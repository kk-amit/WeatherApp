package com.amitss.weatherapp.service.model

/**
 * Response model for all the DB/ API calls.
 */
data class Response<T>(var value: T? = null)