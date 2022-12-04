package com.hackinroms.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hackinroms.weatherforecast.models.Favorite
import kotlinx.coroutines.flow.Flow
import com.hackinroms.weatherforecast.models.Unit

@Dao
interface WeatherDao {
  // Queries for Favorite
  @Query("SELECT * FROM favorite_tbl")
  fun getFavorites(): Flow<List<Favorite>>

  @Query("SELECT * FROM favorite_tbl WHERE id = :id")
  suspend fun getFavoriteById(id: Int): Favorite

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFavorite(favorite: Favorite)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateFavorite(favorite: Favorite)

  @Query("DELETE FROM favorite_tbl")
  suspend fun deleteAllFavorites()

  @Delete
  suspend fun deleteFavorite(favorite: Favorite)

  @Query("SELECT * FROM favorite_tbl WHERE city = :city")
  suspend fun getFavoriteByCity(city: String): Favorite?


  // Queries for Unit
  @Query("SELECT * FROM units_tbl")
  fun getUnits(): Flow<List<Unit>>

  @Query("SELECT * FROM units_tbl WHERE unit = :unit")
  suspend fun getUnitByUnit(unit: String): Unit

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertUnit(unit: Unit)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun updateUnit(unit: Unit)

  @Query("DELETE FROM units_tbl")
  suspend fun deleteAllUnits()

  @Delete
  suspend fun deleteUnit(unit: Unit)

}