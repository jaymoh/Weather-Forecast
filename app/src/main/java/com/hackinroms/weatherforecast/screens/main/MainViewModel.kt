package com.hackinroms.weatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.hackinroms.weatherforecast.data.DataResource
import com.hackinroms.weatherforecast.models.Weather
import com.hackinroms.weatherforecast.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
  val TAG = "MainViewModel"

  suspend fun getWeatherData(city: String, units: String = "metric"): DataResource<Weather, Boolean, Exception> {
    return repository.getWeather(cityQuery = city, units = units)
  }
}