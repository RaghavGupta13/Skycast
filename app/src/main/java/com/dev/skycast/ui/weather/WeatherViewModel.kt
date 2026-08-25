package com.dev.skycast.ui.weather

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.skycast.data.repository.LocationRepository
import com.dev.skycast.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationRepository: LocationRepository): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun onQueryChanged(queryChanged: String){
        _uiState.update {
            it.copy(searchQuery = queryChanged)
        }
    }

    fun fetchWeatherData(){
        val queryString = _uiState.value.searchQuery.trim()

        if(queryString.isEmpty()){
            _uiState.update {
                it.copy(errorMessage = "Please enter a valid US city")
            }
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            try{
                val result = repository.getWeatherByCity(queryString)

                _uiState.update {
                    it.copy(isLoading = false, weather = result, errorMessage = null)
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(isLoading = false, weather = null, errorMessage = e.message ?: "Something went wrong")
                }
            }

        }
    }

    @SuppressLint("MissingPermission")
    fun fetchWeatherForCurrentLocation() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val coordinates = locationRepository.getCurrentLocation()

                if (coordinates == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Current location is unavailable"
                        )
                    }
                    return@launch
                }

                val weather = repository.getWeatherByCoordinates(
                    latitude = coordinates.lat,
                    longitude = coordinates.long
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weather = weather,
                        errorMessage = null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weather = null,
                        errorMessage = exception.message
                            ?: "Unable to retrieve weather for your location"
                    )
                }
            }
        }
    }
}

