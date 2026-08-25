package com.dev.skycast.data.repository

import com.dev.skycast.BuildConfig
import com.dev.skycast.data.mapper.toWeather
import com.dev.skycast.data.remote.OpenWeatherApi
import com.dev.skycast.domain.Weather
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: OpenWeatherApi) {

    suspend fun getWeatherByCity(city: String): Weather {
        return weatherApi.getWeatherByCity(
            city = "${city.trim()},US",
            apiKey = BuildConfig.OPEN_WEATHER_API_KEY
        ).toWeather()
    }

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): Weather {
        return weatherApi.getWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude,
            apiKey = BuildConfig.OPEN_WEATHER_API_KEY
        ).toWeather()
    }
}