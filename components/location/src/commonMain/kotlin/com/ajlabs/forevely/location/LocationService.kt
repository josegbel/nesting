package com.ajlabs.forevely.location

import com.ajlabs.forevely.core.models.PermissionStatus
import com.ajlabs.forevely.location.models.LatLng
import com.ajlabs.forevely.location.models.Location

interface LocationService {
    fun checkPermission(): PermissionStatus
    suspend fun requestPermission()
    fun openSettingPage()
    suspend fun getCurrentLocation(): LatLng?
    suspend fun getPlaceDetails(latitude: Double, longitude: Double): Location?
}
