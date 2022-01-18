package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto

class PostHomeViewModel : ViewModel(){

    private val repository : PostRepository? = PostRepository.getInstance()
    public var postPassengerItems : MutableLiveData<ArrayList<PostPassengerDto>> = MutableLiveData()

    public fun getPassengerPost(){
        repository!!.getPassengerPost(postPassengerItems)
    }
}