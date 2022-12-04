package com.hackinroms.weatherforecast.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackinroms.weatherforecast.models.Favorite
import com.hackinroms.weatherforecast.repositories.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
  ViewModel() {
  private val _favList = MutableStateFlow<List<Favorite>>(emptyList())

  val favList = _favList.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getFavorites()
        .distinctUntilChanged()
        .collect {
          if (it.isEmpty()) {
            Log.d("FavoriteViewModel", "No favorites found")
          } else {
            _favList.value = it
          }
        }
    }
  }

  fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
    // check if favorite already exists
    val fav = repository.getFavoriteByCity(favorite.city)
    if (fav == null) {
      repository.insertFavorite(favorite)
    } else {
      Log.d("FavoriteViewModel", "Favorite already exists")
    }
  }

  fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
    repository.updateFavorite(favorite)
  }

  fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
    Log.d("FavoriteViewModel", "Deleting favorite{$favorite}")
    repository.deleteFavorite(favorite)
  }

  fun deleteAllFavorites() = viewModelScope.launch {
    repository.deleteAllFavorites()
  }
}