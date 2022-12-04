package com.hackinroms.weatherforecast.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "units_tbl")
data class Unit(
  @NonNull
  @PrimaryKey
  @ColumnInfo(name = "unit")
  val unit: String,

  @ColumnInfo(name = "unit_name")
  val unitName: String
)
