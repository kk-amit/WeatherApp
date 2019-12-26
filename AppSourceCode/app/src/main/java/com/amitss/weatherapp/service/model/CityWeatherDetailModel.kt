package com.amitss.weatherapp.service.model

/**
 * Model response City Data.
 */
data class CityWeatherDetailModel(
    val data: Data?
)

data class Data(
    val request: List<Request>?,
    val current_condition: List<CurrentCondition>?,
    val weather: List<Weather>?,
    val climateAverages: List<ClimateAverages>?
)

data class Request(

    val type: String?,
    val query: String?
)

data class CurrentCondition(

    val observation_time: String?,
    val temp_C: Int?,
    val temp_F: Int?,
    val weatherCode: Int?,
    val weatherIconUrl: List<WeatherIconUrl>?,
    val weatherDesc: List<WeatherDesc>?,
    val windspeedMiles: Int?,
    val windspeedKmph: Int?,
    val winddirDegree: Int?,
    val winddir16Point: String?,
    val precipMM: Double?,
    val precipInches: Double?,
    val humidity: Int?,
    val visibility: Int?,
    val visibilityMiles: Int?,
    val pressure: Int?,
    val pressureInches: Int?,
    val cloudcover: Int?,
    val feelsLikeC: Int?,
    val feelsLikeF: Int?,
    val uvIndex: Int?
)

data class Weather(

    val date: String?,
    val astronomy: List<Astronomy>?,
    val maxtempC: Int?,
    val maxtempF: Int?,
    val mintempC: Int?,
    val mintempF: Int?,
    val avgtempC: Int?,
    val avgtempF: Int?,
    val totalSnow_cm: Double?,
    val sunHour: Double?,
    val uvIndex: Int?,
    val hourly: List<Hourly>?
)

data class Astronomy(
    val sunrise: String?,
    val sunset: String?,
    val moonrise: String?,
    val moonset: String?,
    val moon_phase: String?,
    val moon_illumination: Int?
)

data class ClimateAverages(
    val month: List<Month>?
)

data class Month(
    val index: Int?,
    val name: String?,
    val avgMinTemp: Double?,
    val avgMinTemp_F: Double?,
    val absMaxTemp: Double?,
    val absMaxTemp_F: Double?,
    val avgDailyRainfall: Double?
)

data class Hourly(

    val time: Int?,
    val tempC: Int?,
    val tempF: Int?,
    val windspeedMiles: Int?,
    val windspeedKmph: Int?,
    val winddirDegree: Int?,
    val winddir16Point: String?,
    val weatherCode: Int?,
    val weatherIconUrl: List<WeatherIconUrl>?,
    val weatherDesc: List<WeatherDesc>?,
    val precipMM: Double?,
    val precipInches: Double?,
    val humidity: Int?,
    val visibility: Int?,
    val visibilityMiles: Int?,
    val pressure: Int?,
    val pressureInches: Int?,
    val cloudcover: Int?,
    val heatIndexC: Int?,
    val heatIndexF: Int?,
    val dewPointC: Int?,
    val dewPointF: Int?,
    val windChillC: Int?,
    val windChillF: Int?,
    val windGustMiles: Int?,
    val windGustKmph: Int?,
    val feelsLikeC: Int?,
    val feelsLikeF: Int?,
    val chanceofrain: Int?,
    val chanceofremdry: Int?,
    val chanceofwindy: Int?,
    val chanceofovercast: Int?,
    val chanceofsunshine: Int?,
    val chanceoffrost: Int?,
    val chanceofhightemp: Int?,
    val chanceoffog: Int?,
    val chanceofsnow: Int?,
    val chanceofthunder: Int?,
    val uvIndex: Int?
)

data class WeatherIconUrl(
    val value: String?
)

data class WeatherDesc(
    val value: String?
)