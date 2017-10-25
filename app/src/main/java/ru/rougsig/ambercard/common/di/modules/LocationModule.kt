package ru.rougsig.ambercard.common.di.modules

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

/**
 * Created by rougs on 25-Oct-17.
 */
@SuppressLint("MissingPermission")
object LocationModule {
    var location: Location? = null

    fun create(context: Context) {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val listener = object : LocationListener {
            override fun onLocationChanged(p0: Location) { location = p0 }
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
            override fun onProviderEnabled(p0: String?) {}
            override fun onProviderDisabled(p0: String?) {}
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000L,
                10f,
                listener)
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
    }
}