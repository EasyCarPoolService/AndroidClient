package com.example.easycarpoolapp.fragment.location.utils

import android.content.Context
import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.fragment.location.dto.LocationDto
import net.daum.mf.map.api.*

class MapUtils private constructor(val mapView : MapView, val context : Context){

    companion object{
        public fun getInstance(mapView:MapView, context: Context): MapUtils{
            return MapUtils(mapView, context)
        }
    }//companion object

    private var currentMyLocation : MapPOIItem? = null
    private var currentOpponentLocation : MapPOIItem? = null


    public fun moveToCenter(location: Location){
        val mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
        mapView.setMapCenterPoint(mapPoint,true)
        //지도에서 내 위치를 가운데로 지정
        /*val marker = MapPOIItem()
        marker.itemName = "내위치"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)*/
    }//moveToCenter

    fun markMyLocation(locationDto : LocationDto?){
        if (locationDto==null) return
        val mapPoint = MapPoint.mapPointWithGeoCoord(locationDto.lat, locationDto.lon)
        mapView.setMapCenterPoint(mapPoint,true)
        //지도에서 내 위치를 가운데로 지정

        if(currentMyLocation!=null){    //현재 mapView에 marker가 추가 되어있는 경우 제거
            mapView.removePOIItem(currentMyLocation)
        }
        val marker = MapPOIItem()
        marker.itemName = "myLocation"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
        currentMyLocation = marker
        mapView.addPOIItem(currentMyLocation)
    }

    fun markOpponentLocation(locationDto : LocationDto?) {
        if (locationDto==null) return
        val mapPoint = MapPoint.mapPointWithGeoCoord(locationDto.lat, locationDto.lon)
        mapView.setMapCenterPoint(mapPoint,true)
        //지도에서 내 위치를 가운데로 지정

        if(currentOpponentLocation != null){    //현재 mapView에 marker가 추가 되어있는 경우 제거
            mapView.removePOIItem(currentOpponentLocation)
        }
        val marker = MapPOIItem()
        marker.itemName = "opponentLocation"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.RedPin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        currentOpponentLocation = marker
        mapView.addPOIItem(currentOpponentLocation)

    }//상대방의 위치 표기 하기

}