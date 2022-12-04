package com.hackinroms.weatherforecast.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hackinroms.weatherforecast.models.Favorite
import com.hackinroms.weatherforecast.navigation.WeatherScreens
import com.hackinroms.weatherforecast.screens.favorite.FavoriteViewModel

//@Preview
@Composable
fun WeatherAppBar(
  title: String = "Weather Forecast",
  icon: ImageVector? = null,
  isMainScreen: Boolean = true,
  elevation: Dp = 0.dp,
  navController: NavController,
  favoriteViewModel: FavoriteViewModel = hiltViewModel(),
  onSearchActionClicked: () -> Unit = {},
  onRefreshActionClicked: () -> Unit = {},
  onButtonClicked: () -> Unit = {},
) {
  val showDialog = remember {
    mutableStateOf(false)
  }

  val context = LocalContext.current
  val showToast = remember {
    mutableStateOf(false)
  }
  val toastMessage = remember {
    mutableStateOf("")
  }

  if (showDialog.value) {
    ShowSettingsDropDownDialog(navController = navController, showDialog = showDialog)
  }
  TopAppBar(
    title = {
      Text(
        text = title,
        color = MaterialTheme.colors.onSurface,
        style = TextStyle(
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold
        )
      )
    },
    actions = {
      if (isMainScreen) {
        IconButton(onClick = {
          onRefreshActionClicked.invoke()
        }) {
          Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon")
        }

        IconButton(onClick = {
          onSearchActionClicked.invoke()
        }) {
          Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        }
        IconButton(onClick = {
          showDialog.value = true
        }) {
          Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icon")
        }
      } else Box { }
    },
    navigationIcon = {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaterialTheme.colors.onSecondary,
          modifier = Modifier.clickable {
            onButtonClicked.invoke()
          }
        )
      }
      if (isMainScreen) {
        val cityData = title.split(",")

        val isAlreadyFavorite = favoriteViewModel
          .favList.collectAsState().value.any { it.city == cityData[0] }

        if (!isAlreadyFavorite) {
          Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Favorite Icon",
            tint = Color.Red.copy(alpha = 0.6f),
            modifier = Modifier
              .scale(0.9f)
              .clickable {
                favoriteViewModel.insertFavorite(
                  Favorite(
                    city = cityData[0],
                    country = cityData[1]
                  )
                ).run {
                  showToast.value = true
                  toastMessage.value = "Added to Favorites"
                }
              },
          )
        } else {
          val fav = favoriteViewModel
            .favList.collectAsState().value.find { it.city == cityData[0] }
          Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Favorite Icon",
            tint = Color.Red.copy(alpha = 0.6f),
            modifier = Modifier
              .scale(0.8f)
              .clickable {
                if (fav != null) {
                  favoriteViewModel.deleteFavorite(fav).run {
                    showToast.value = true
                    toastMessage.value = "Removed from Favorites"
                  }
                }
              },
          )
        }

        ShowToast(context = context, showToast = showToast, message = toastMessage.value)
      }
    },
    backgroundColor = MaterialTheme.colors.primary,
    elevation = elevation
  )
}

@Composable
fun ShowSettingsDropDownDialog(
  navController: NavController,
  showDialog: MutableState<Boolean>
) {
  val expanded = remember { mutableStateOf(true) }
  val menuItems = listOf("Favorites", "Settings", "About")

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentSize(Alignment.TopEnd)
      .absolutePadding(top = 45.dp, right = 20.dp)
  ) {
    DropdownMenu(
      expanded = expanded.value,
      onDismissRequest = {
        expanded.value = false
        showDialog.value = false
      },
      modifier = Modifier
        .width(150.dp)
        .background(MaterialTheme.colors.background)
    ) {
      menuItems.forEachIndexed { index, text ->
        DropdownMenuItem(onClick = {
          expanded.value = false
          showDialog.value = false
          when (index) {
            0 -> navController.navigate(WeatherScreens.FavoriteScreen.name)
            1 -> navController.navigate(WeatherScreens.SettingsScreen.name)
            2 -> navController.navigate(WeatherScreens.AboutScreen.name)
          }
        }) {
          Icon(
            imageVector = when (index) {
              0 -> Icons.Default.FavoriteBorder
              1 -> Icons.Default.Settings
              2 -> Icons.Default.Info
              else -> Icons.Default.Settings
            },
            contentDescription = null,
            tint = Color.LightGray,
          )
          Text(
            text = text,
            fontWeight = FontWeight.W300,
          )
        }
      }
    }
  }
}


@Composable
fun ShowToast(context: Context, showToast: MutableState<Boolean>, message: String) {
  if (showToast.value) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    showToast.value = false
  }
}
