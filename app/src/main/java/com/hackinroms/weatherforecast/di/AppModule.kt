package com.hackinroms.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.hackinroms.weatherforecast.api.OpenWeatherApi
import com.hackinroms.weatherforecast.data.WeatherDao
import com.hackinroms.weatherforecast.data.WeatherDatabase
import com.hackinroms.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  @Singleton
  @Provides
  fun provideOpenWeatherApi(): OpenWeatherApi {
    return Retrofit.Builder()
      .baseUrl(Constants.BASE_VERSIONED_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(OpenWeatherApi::class.java)
  }

  @Singleton
  @Provides
  fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
    weatherDatabase.weatherDao() // gives us access to the WeatherDao

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
    Room.databaseBuilder(
      context,
      WeatherDatabase::class.java,
      "weather_db"
    )
      .fallbackToDestructiveMigration()
      .build()

}