package com.hackinroms.weatherforecast.repositories

import com.hackinroms.weatherforecast.data.WeatherDao
import com.hackinroms.weatherforecast.models.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.hackinroms.weatherforecast.models.Unit

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
  // Queries for Favorite
  fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
  suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
  suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
  suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
  suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
  suspend fun getFavoriteById(id: Int) = weatherDao.getFavoriteById(id)
  suspend fun getFavoriteByCity(city: String) = weatherDao.getFavoriteByCity(city)

  // Queries for Unit
  fun getUnits():Flow<List<Unit>> = weatherDao.getUnits()
  suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
  suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
  suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
  suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
  suspend fun getUnitByUnit(unit: String) = weatherDao.getUnitByUnit(unit)
}