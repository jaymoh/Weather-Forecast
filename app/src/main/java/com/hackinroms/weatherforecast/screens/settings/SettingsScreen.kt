package com.hackinroms.weatherforecast.screens.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hackinroms.weatherforecast.models.Unit
import com.hackinroms.weatherforecast.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
  navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()
) {
  val unitToggleState = remember { mutableStateOf(false) }
  // val measurementUnits = listOf("Metric (C)", "Imperial (F)")
  val measurementValues = listOf("Celsius °C", "Fahrenheit °F")
  val choiceFromDb = settingsViewModel.unitList.collectAsState().value
  val defaultChoice = if (choiceFromDb.isEmpty()) measurementValues[0] else choiceFromDb[0].unitName

  val choiceState = remember { mutableStateOf("") }
  choiceState.value = defaultChoice

  Scaffold(topBar = {
    WeatherAppBar(
      title = "Settings",
      icon = Icons.Default.ArrowBack,
      isMainScreen = false,
      navController = navController
    ) { navController.popBackStack() }
  }) {

    Surface(modifier = Modifier.fillMaxSize()) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "Change Units of Measurement",
          modifier = Modifier.padding(bottom = 15.dp)
        )

        IconToggleButton(
          checked = !unitToggleState.value,
          onCheckedChange = {
            unitToggleState.value = !unitToggleState.value

            choiceState.value = if (choiceState.value == measurementValues[0]) {
              measurementValues[1]
            } else {
              measurementValues[0]
            }
          },
          modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RectangleShape)
            .padding(5.dp)
            .background(color = MaterialTheme.colors.primary)
        ) {

          Text(
            text = choiceState.value,
            modifier = Modifier.padding(10.dp),
            color = Color.White
          )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
          onClick = {
            settingsViewModel.deleteAllUnits()

            settingsViewModel.insertUnit(
              Unit(
                unit = if (choiceState.value == measurementValues[0]) {
                  "metric"
                } else {
                  "imperial"
                },
                unitName = choiceState.value
              )
            )
          },
          modifier = Modifier
            .padding(3.dp)
            .align(Alignment.CenterHorizontally),
          shape = RoundedCornerShape(34.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFEFBE42),
          )
        ) {
          Text(
            text = "Save",
            color = Color.White,
            modifier = Modifier.padding(5.dp),
            fontSize = 17.sp
          )
        }
      }
    }
  }
}
