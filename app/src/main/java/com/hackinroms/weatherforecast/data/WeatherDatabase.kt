package com.hackinroms.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hackinroms.weatherforecast.models.Favorite
import com.hackinroms.weatherforecast.models.Unit

@Database(entities = [Favorite::class, Unit::class], version = 3, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
  abstract fun weatherDao(): WeatherDao
}