package com.hackinroms.weatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackinroms.weatherforecast.repositories.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.hackinroms.weatherforecast.models.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
  ViewModel() {

  private val _unitList = MutableStateFlow<List<Unit>>(emptyList())

  val unitList = _unitList.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getUnits()
        .distinctUntilChanged()
        .collect {
          if (it.isEmpty()) {
            Log.d("SettingsViewModel", "No units found")
          } else {
            _unitList.value = it
          }
        }
    }
  }

  fun insertUnit(unit: Unit) = viewModelScope.launch {
    repository.insertUnit(unit)
  }

  fun updateUnit(unit: Unit) = viewModelScope.launch {
    repository.updateUnit(unit)
  }

  fun deleteUnit(unit: Unit) = viewModelScope.launch {
    repository.deleteUnit(unit)
  }

  fun deleteAllUnits() = viewModelScope.launch {
    repository.deleteAllUnits()
  }

  fun getUnitByUnit(unit: String) = viewModelScope.launch {
    repository.getUnitByUnit(unit)
  }
}