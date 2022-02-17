package com.example.easycarpoolapp.fragment.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.dto.PostDto

class CalendarHomeViewModel : ViewModel(){
    private val repository : CalendarRepository? = CalendarRepository.getInstance()
    public var postItems : MutableLiveData<ArrayList<PostDto>> = MutableLiveData()

    public fun getPostData(){
        repository!!.getPostData(postItems)
    }//getPostData()

}