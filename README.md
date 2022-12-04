## Weather Forecast Android App

Made with:
- Kotlin
- Jetpack Compose
- Android Retrofit
- Android Room
- Android Navigation
- Android ViewModel
- Kotlin Flow and Coroutines
- Dependency Injection with Hilt and Dagger
- Image loading with Coil

Using the [OpenWeatherMap API](https://openweathermap.org/api) to get the weather forecast. 
You need to get an API key and specify it in ``com/hackinroms/weatherforecast/utils/Constants.kt``

Following one of the best tutorials on the internet: [Kotlin Android Jetpack Compose](https://www.udemy.com/course/kotling-android-jetpack-compose-/)

I added a few things to the app:
- Updated the dependencies to the latest versions (as of 2022, December)
- Set the app to load the weather forecast for the first saved location in the Favorites list.
- The heart icon will be filled if the location is in the Favorites list, and empty otherwise.
- Clicking on the heart icon will add/remove the location to/from the Favorites list.
- Added a refresh button to the App Bar to refresh the weather forecast data.
- Pressing the back button will close the app if the current screen is the Home screen, and go back to the Home screen otherwise.