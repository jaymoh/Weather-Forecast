package com.hackinroms.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.hackinroms.weatherforecast.R
import com.hackinroms.weatherforecast.models.WeatherItem
import com.hackinroms.weatherforecast.utils.formatDate
import com.hackinroms.weatherforecast.utils.formatDecimals
import com.hackinroms.weatherforecast.utils.formatTime


@Composable
fun SunsetSunriseRow(weatherItem: WeatherItem) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.padding(4.dp),
    ){
      Icon(
        painter = painterResource(id = R.drawable.sunrise),
        contentDescription = "Sunrise",
        modifier = Modifier.size(25.dp)
      )
      Text(
        text =  formatTime(weatherItem.sunrise),
        style = MaterialTheme.typography.caption,
      )
    }

    Row(
      modifier = Modifier.padding(4.dp),
    ){
      Icon(
        painter = painterResource(id = R.drawable.sunset),
        contentDescription = "Sunset",
        modifier = Modifier.size(25.dp)
      )
      Text(
        text =  formatTime(weatherItem.sunset),
        style = MaterialTheme.typography.caption,
      )
    }
  }
}

@Composable
fun HumidityWindPressureRow(weatherItem: WeatherItem, unit: String) {
  val speedToShow = if (unit == "metric") "m/s" else "mph"

  Row(
    modifier = Modifier
      .padding(4.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      modifier = Modifier
        .padding(4.dp)
    ){
      Icon(
        painter = painterResource(id = R.drawable.humidity),
        contentDescription = "Humidity",
        modifier = Modifier.size(25.dp)
      )
      Text(text =  "${weatherItem.humidity}%")
    }

    Row(
      modifier = Modifier
        .padding(4.dp)
    ){
      Icon(
        painter = painterResource(id = R.drawable.pressure),
        contentDescription = "Pressure",
        modifier = Modifier.size(25.dp)
      )
      Text(text =  "${weatherItem.pressure} psi")
    }

    Row(
      modifier = Modifier
        .padding(4.dp)
    ){
      Icon(
        painter = painterResource(id = R.drawable.wind),
        contentDescription = "Wind",
        modifier = Modifier.size(25.dp)
      )
      Text(text =  "${weatherItem.speed} $speedToShow")
    }

  }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
  Image(
    painter = rememberAsyncImagePainter(imageUrl),
    contentDescription = "Weather State",
    modifier = Modifier.size(80.dp)
  )
}

@Composable
fun ForecastList(weatherList: List<WeatherItem>) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .padding(2.dp),
    shape = RoundedCornerShape(10.dp),
    color = if (MaterialTheme.colors.isLight) Color(0xFFF1F3F4) else Color(0xFF424242)
// 0XFFEEF1EF
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp),
      contentPadding = PaddingValues(0.dp),
    ) {
      items(weatherList) { item ->
        ForecastItem(weatherItem = item)
      }
    }
  }
}

@Composable
fun ForecastItem(weatherItem: WeatherItem) {
  val imageUrl = "https://openweathermap.org/img/wn/" + weatherItem.weather[0].icon + ".png"
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(3.dp),
    shape = CircleShape.copy(
      topEnd = CornerSize(6.dp)
    ),
    color = if (MaterialTheme.colors.isLight) Color.White else Color(0xFFABABAB)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {

      Text(
        text = formatDate(weatherItem.dt, "EEE"),
        modifier = Modifier.padding(6.dp),
      )

      WeatherStateImage(imageUrl = imageUrl)

      Surface(
        modifier = Modifier.padding(0.dp),
        shape = CircleShape,
        color = Color(0xFFFFC400)

      ) {
        Text(text = weatherItem.weather[0].description,
          modifier = Modifier.padding(4.dp),
          fontStyle = MaterialTheme.typography.caption.fontStyle,
        )
      }

      Text(
        text = buildAnnotatedString {
          withStyle(style = SpanStyle(
            color = Color.Blue.copy(alpha = 0.7f),
            fontWeight = FontWeight.SemiBold
          )
          ) {
            append(formatDecimals(weatherItem.temp.max) + "° ")
          }
          withStyle(style = SpanStyle(
            color = if (MaterialTheme.colors.isLight) Color(0xFFABABAB) else Color(0xFF424242),
          )
          ) {
            append(formatDecimals(weatherItem.temp.min) + "°")
          }
        }
      )
    }

  }
}