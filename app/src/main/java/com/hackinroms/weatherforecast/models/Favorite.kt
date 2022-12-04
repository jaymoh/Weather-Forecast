package com.hackinroms.weatherforecast.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tbl")
data class Favorite(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,

  @NonNull
  @ColumnInfo(name = "city")
  val city: String,

  @ColumnInfo(name = "country")
  val country: String
)
