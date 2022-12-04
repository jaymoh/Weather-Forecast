package com.hackinroms.weatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hackinroms.weatherforecast.R
import com.hackinroms.weatherforecast.navigation.WeatherScreens
import com.hackinroms.weatherforecast.screens.favorite.FavoriteViewModel
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(
  navController: NavHostController,
  favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {

  val scale = remember {
    Animatable(0f)
  }

  val cityFromDb = favoriteViewModel.favList.collectAsState().value
  val defaultCity = remember {
    mutableStateOf("nairobi")
  }
  defaultCity.value = if (cityFromDb.isNotEmpty()) {
    cityFromDb[0].city
  } else {
    "nairobi"
  }

  LaunchedEffect(
    key1 = true,
    block = {
      scale.animateTo(
        targetValue = 0.9f,
        animationSpec = tween(
          durationMillis = 1500,
          delayMillis = 1000,
          easing = {
            OvershootInterpolator(10f).getInterpolation(it)
          }
        )
      )
      delay(2000L)
      // navigate to main screen
      navController.navigate(WeatherScreens.MainScreen.name + "?cityName=${defaultCity.value}")
    }
  )

  Surface(
    modifier = Modifier
      .padding(15.dp)
      .size(330.dp)
      .scale(scale.value),
    shape = CircleShape,
    color = MaterialTheme.colors.background,
    border = BorderStroke(
      width = 2.dp,
      color = MaterialTheme.colors.primary
    )
  ) {
    Column(
      modifier = Modifier.padding(1.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Image(
        painter = painterResource(id = R.drawable.cloudy),
        contentDescription = "Weather Forecast",
        modifier = Modifier.size(100.dp),
        contentScale = ContentScale.Fit
      )

      Text(
        text = "Weather Forecast",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier.padding(10.dp)
      )
    }
  }
}
