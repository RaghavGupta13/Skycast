package com.dev.skycast.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.dev.skycast.domain.Coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun getCurrentLocation(): Coordinates? {

        if(!hasLocationPermission()) return null

        val location = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).await()

        return location?.let {
            Coordinates(
                lat = it.latitude,
                long = it.longitude
            )
        }
    }

    private fun hasLocationPermission(): Boolean{
        val coarsePermissionGranted =  (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)

        val finePermissionGranted = (ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)

        return coarsePermissionGranted || finePermissionGranted
    }
}