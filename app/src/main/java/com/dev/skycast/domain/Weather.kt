package com.dev.skycast.domain

data class Weather(
    val cityName: String,
    val country: String,
    val temperature: Int,
    val feelsLike: Int,
    val minimumTemperature: Int,
    val maximumTemperature: Int,
    val description: String,
    val iconUrl: String,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val sunrise: Int,
    val sunset: Int,
    val timezoneOffset: Int
)