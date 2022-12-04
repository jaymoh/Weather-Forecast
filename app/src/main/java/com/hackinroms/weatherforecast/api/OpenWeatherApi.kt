package com.hackinroms.weatherforecast.api

import com.hackinroms.weatherforecast.models.Weather
import com.hackinroms.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface OpenWeatherApi {
  @GET(value = "forecast/daily")
  suspend fun getWeather(
    @Query("q") city: String,
    @Query("units") units: String = "metric",
    @Query("appid") apiKey: String = Constants.API_KEY
  ): Weather
}