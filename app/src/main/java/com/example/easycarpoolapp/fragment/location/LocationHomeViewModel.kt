package com.example.easycarpoolapp.fragment.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto

class LocationHomeViewModel : ViewModel() {

    val repository : LocationRepository? = LocationRepository.getInstance()
    val items : MutableLiveData<ArrayList<ReservedPostDto>> = MutableLiveData()

    public fun getReservedPost(){
        repository?.getReservedPost(items, LocalUserData.getEmail())
    }//getReservedPost

}