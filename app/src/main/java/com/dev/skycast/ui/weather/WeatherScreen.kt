package com.dev.skycast.ui.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dev.skycast.domain.Weather

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(uiState: WeatherUiState, onQueryChanged: (String) -> Unit, fetchWeather: () -> Unit){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Skycast")
                }
            )
        }
    ){
        paddingValues ->

        Column(modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            CitySearchBar(
                uiState,
                onQueryChanged
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = fetchWeather,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(32.dp))

            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                uiState.weather != null -> {
                    WeatherContent(
                        weather = uiState.weather
                    )
                }
            }
        }
    }

}

@Composable
fun WeatherContent(
    weather: Weather
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "${weather.cityName}, ${weather.country}",
            style = MaterialTheme.typography.headlineMedium
        )

        AsyncImage(
            model = weather.iconUrl,
            contentDescription = weather.description,
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = "${weather.temperature}°F",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = weather.description.replaceFirstChar {
                it.uppercase()
            },
            style = MaterialTheme.typography.titleMedium
        )

        Text("Feels like ${weather.feelsLike}°F")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Humidity: ${weather.humidity}%")
        Text("Wind: ${weather.windSpeed} mph")
        Text("Pressure: ${weather.pressure} hPa")

        Text(
            text = "Low: ${weather.minimumTemperature}°F   " +
                    "High: ${weather.maximumTemperature}°F"
        )
    }
}

@Composable
fun CitySearchBar(
    uiState: WeatherUiState,
    onQueryChanged : (String) -> Unit){

    OutlinedTextField(
        value = uiState.searchQuery,
        onValueChange = onQueryChanged,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text("Enter a US city")
        },
        placeholder = {
            Text("Example: Phoenix, AZ")
        },
        singleLine = true
    )
}