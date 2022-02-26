package com.example.easycarpoolapp.fragment.location

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.location.dto.LocationDto

class LocationHomeViewModel : ViewModel() {

    val repository : LocationRepository? = LocationRepository.getInstance()
    val items : MutableLiveData<ArrayList<ReservedPostDto>> = MutableLiveData()
    val opponentLocation : MutableLiveData<LocationDto> = MutableLiveData()

    public fun getReservedPost(){
        repository?.getReservedPost(items, LocalUserData.getEmail())
    }//getReservedPost

    public fun subscribe(roomId : String){
        repository?.subscribe(roomId = roomId, opponentLocation = opponentLocation)
    } //subscribe

    public fun sendLocation(roomId : String, location: Location) {
        repository?.sendLocation(roomId, location)
    }

}