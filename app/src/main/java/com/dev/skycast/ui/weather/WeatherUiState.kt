package com.dev.skycast.ui.weather

import com.dev.skycast.domain.Weather

data class WeatherUiState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val errorMessage: String? = null
)