package com.hackinroms.weatherforecast.repositories

import com.hackinroms.weatherforecast.api.OpenWeatherApi
import com.hackinroms.weatherforecast.data.DataResource
import com.hackinroms.weatherforecast.models.Weather
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherApi: OpenWeatherApi) {

    suspend fun getWeather(cityQuery: String, units: String = "metric"): DataResource<Weather, Boolean, Exception> {
        return try {
            val weather = weatherApi.getWeather(cityQuery, units)
            DataResource(data = weather, loading = false, error = null)
        } catch (e: Exception) {
            DataResource(data = null, loading = false, error = e)
        }
    }
}
