package com.hackinroms.weatherforecast.screens.about

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hackinroms.weatherforecast.R
import com.hackinroms.weatherforecast.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController) {
  Scaffold(
    topBar = {
      WeatherAppBar(
        title = "About",
        icon = Icons.Default.ArrowBack,
        isMainScreen = false,
        navController = navController,
      ) {
        navController.popBackStack()
      }
    }
  ) {
    Surface(
      modifier = Modifier.fillMaxSize(),
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
      ) {

        Text(
          text = stringResource(R.string.about_text),
          style = MaterialTheme.typography.subtitle1,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(16.dp)
        )

        Text(
          text = stringResource(R.string.api_used),
          style = MaterialTheme.typography.subtitle1,
          fontWeight = FontWeight.Light,
          modifier = Modifier.padding(16.dp)
        )
      }
    }
  }
}
