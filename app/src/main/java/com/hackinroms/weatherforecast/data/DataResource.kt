package com.hackinroms.weatherforecast.data

class DataResource<T, Boolean, Exception>(
  var data: T? = null,
  var loading: Boolean? = null,
  var error: Exception? = null
)
