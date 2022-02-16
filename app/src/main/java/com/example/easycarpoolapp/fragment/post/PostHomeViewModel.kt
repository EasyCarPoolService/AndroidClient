package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto

class PostHomeViewModel : ViewModel(){

    private val repository : PostRepository? = PostRepository.getInstance()
    public var postItems : MutableLiveData<ArrayList<PostDto>> = MutableLiveData()

    public fun getPassengerPost(){
        repository!!.getPassengerPost(postItems)
    }

    public fun getDriverPost(){
        repository!!.getDriverPost(postItems)
    }

    //Driver 인증 여부 결과 받아 LocalUserData 업데이트 수행
    public fun getDriverAuth(){
        repository!!.getDriverAuth()
    }


}