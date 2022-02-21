package com.example.easycarpoolapp.fragment.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto

class CalendarHomeViewModel : ViewModel(){
    private val repository : CalendarRepository? = CalendarRepository.getInstance()
    public var postItems : MutableLiveData<ArrayList<ReservedPostDto>> = MutableLiveData()

    public fun getPostData(){
        repository!!.getPostData(postItems)
    }//getPostData()

}