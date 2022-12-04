package com.hackinroms.weatherforecast.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.hackinroms.weatherforecast.data.DataResource
import com.hackinroms.weatherforecast.models.Weather
import com.hackinroms.weatherforecast.navigation.WeatherScreens
import com.hackinroms.weatherforecast.screens.favorite.FavoriteViewModel
import com.hackinroms.weatherforecast.screens.settings.SettingsViewModel
import com.hackinroms.weatherforecast.utils.formatDate
import com.hackinroms.weatherforecast.utils.formatDecimals
import com.hackinroms.weatherforecast.widgets.*
import kotlin.system.exitProcess

@Composable
fun MainScreen(
  navController: NavHostController,
  mainViewModel: MainViewModel = hiltViewModel(),
  settingsViewModel: SettingsViewModel = hiltViewModel(),
  favoriteViewModel: FavoriteViewModel = hiltViewModel(),
  cityName: String?
) {
  val unitFromDb = settingsViewModel.unitList.collectAsState().value
  val defaultUnit = if (unitFromDb.isEmpty()) "metric" else unitFromDb[0].unit

  var defaultCity = cityName

  if (cityName == null) {
    val cityFromDb = favoriteViewModel.favList.collectAsState().value
    defaultCity = if (cityFromDb.isEmpty()) "nairobi" else cityFromDb[0].city
  }

  val data = produceState<DataResource<Weather, Boolean, Exception>>(
    initialValue = DataResource(loading = true)
  ) {
    value = mainViewModel.getWeatherData(
      city = defaultCity!!,
      units = defaultUnit
    ) // Nairobi is a city in Kenya
  }.value

  if (data.loading == true) {
    CircularProgressIndicator()
  } else if (data.data != null) {
    MainScaffold(weatherData = data.data!!, navController = navController, unit = defaultUnit)
  } else if (data.error != null) {
    Text(text = data.error!!.message.toString())
  }

  BackHandler {
    // current route
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    if (currentRoute != null) {
      if (currentRoute.contains(WeatherScreens.MainScreen.name, ignoreCase = true)) {
        exitProcess(0)
      }
    }
  }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weatherData: Weather, navController: NavController, unit: String) {
  Scaffold(
    topBar = {
      WeatherAppBar(
        title = weatherData.city.name + ", " + weatherData.city.country,
        navController = navController,
        elevation = 5.dp,
        onSearchActionClicked = {
          navController.navigate(WeatherScreens.SearchScreen.name)
        },
        onRefreshActionClicked = {
          navController.navigate(WeatherScreens.MainScreen.name + "?cityName=${weatherData.city.name}")
        }
      ) { }
    }
  ) {
    MainContent(data = weatherData, unit = unit)
  }
}

@Composable
fun MainContent(data: Weather, unit: String) {
  val weatherItem = data.list[0]
  val imageUrl = "https://openweathermap.org/img/wn/" + weatherItem.weather[0].icon + ".png"
  val unitToShow = if (unit == "metric") "°C" else "°F"

  Column(
    modifier = Modifier
      .padding(4.dp)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = formatDate(weatherItem.dt),
      style = MaterialTheme.typography.caption,
      color = MaterialTheme.colors.onBackground,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(6.dp)
    )

    Surface(
      modifier = Modifier.padding(4.dp).size(200.dp),
      shape = CircleShape,
      color = Color(0xFFFFC400)
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

        WeatherStateImage(imageUrl = imageUrl)

        Text(
          text = formatDecimals(weatherItem.temp.day) + unitToShow,
          style = MaterialTheme.typography.h4,
          fontWeight = FontWeight.ExtraBold,
        )
        Text(
          text = weatherItem.weather[0].description,
          fontStyle = FontStyle.Italic
        )
      }
    }

    HumidityWindPressureRow(weatherItem = weatherItem, unit)

    Divider(modifier = Modifier.padding(5.dp))

    SunsetSunriseRow(weatherItem = weatherItem)

    Text(
      text = "This Week",
      style = MaterialTheme.typography.subtitle1,
      color = MaterialTheme.colors.onBackground,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(6.dp)
    )

    ForecastList(weatherList = data.list)
  }
}
