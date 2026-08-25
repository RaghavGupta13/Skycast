package com.dev.skycast.data.mapper

import com.dev.skycast.domain.Weather
import com.dev.skycast.data.remote.WeatherResponseDto
import kotlin.math.roundToInt

fun WeatherResponseDto.toWeather(): Weather {

    val primaryCondition = weather.firstOrNull()

    return Weather(
        cityName = name,
        country = sys.country,
        temperature = main.temp.roundToInt(),
        feelsLike = main.feels_like.roundToInt(),
        minimumTemperature = main.temp_min.roundToInt(),
        maximumTemperature = main.temp_min.roundToInt(),
        description = primaryCondition?.description.orEmpty(),
        iconUrl = primaryCondition?.icon
            ?.let { iconCode ->
                "https://openweathermap.org/img/wn/${iconCode}@2x.png"
            }
            .orEmpty(),
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        timezone
    )

}