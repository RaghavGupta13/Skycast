package com.dev.skycast.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String
    ): WeatherResponseDto

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
        @Query("appid") apiKey: String
    ): WeatherResponseDto

}