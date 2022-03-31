package com.example.easycarpoolapp.fragment.location.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


@SuppressLint("MissingPermission")
class FusedLocationManager (val hostFragment : Fragment, val context : Context){

    interface Callback{
        fun setMapByLocation(location : Location)
    } //Callback

    private var callback : Callback? = null
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var locationRequest : com.google.android.gms.location.LocationRequest
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            callback?.setMapByLocation(locationResult.lastLocation)
        }
    }

    public fun setLocationClient(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            interval = 1000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    public fun onDestroy(){
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    init{
        callback = hostFragment as Callback
    }


}