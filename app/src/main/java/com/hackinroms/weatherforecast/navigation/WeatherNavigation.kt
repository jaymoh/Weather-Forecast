package com.hackinroms.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hackinroms.weatherforecast.screens.about.AboutScreen
import com.hackinroms.weatherforecast.screens.favorite.FavoriteScreen
import com.hackinroms.weatherforecast.screens.main.MainScreen
import com.hackinroms.weatherforecast.screens.main.MainViewModel
import com.hackinroms.weatherforecast.screens.search.SearchScreen
import com.hackinroms.weatherforecast.screens.settings.SettingsScreen
import com.hackinroms.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = WeatherScreens.SplashScreen.name
  ) {
    // routes or screens

    composable(route = WeatherScreens.SplashScreen.name) {
      WeatherSplashScreen(navController = navController)
    }

    val mainScreenRoute = WeatherScreens.MainScreen.name
    composable(
      route = "$mainScreenRoute?cityName={cityName}",
      arguments = listOf(
        navArgument(name = "cityName") {
          type = NavType.StringType
          nullable = true
        }
      )
    ) { navBack ->
      navBack.arguments?.getString("cityName").let { cityName ->
        val mainViewModel = hiltViewModel<MainViewModel>()
        MainScreen(
          navController = navController,
          mainViewModel = mainViewModel,
          cityName = cityName,
        )
      }
    }

    composable(route = WeatherScreens.SearchScreen.name) {
      SearchScreen(navController = navController)
    }

    composable(route = WeatherScreens.FavoriteScreen.name) {
      FavoriteScreen(navController = navController)
    }

    composable(route = WeatherScreens.SettingsScreen.name) {
      SettingsScreen(navController = navController)
    }

    composable(route = WeatherScreens.AboutScreen.name) {
      AboutScreen(navController = navController)
    }
  }
}