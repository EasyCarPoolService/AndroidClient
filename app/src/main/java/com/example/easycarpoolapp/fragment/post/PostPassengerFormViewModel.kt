package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto

class PostPassengerFormViewModel :ViewModel(){

    // PostPassengerFormFragment를 Hosting하는 Activity에서 PostRepository.init(context)수행
    private val repository : PostRepository? = PostRepository.getInstance()


    public var departure : String? = null
    public var destination : String? = null
    public var departureDate : String? = null
    public var departuretime : String? = null
    public var gift : ArrayList<String> = ArrayList() //transform to arrayList or hashMap
    public var hashTag : String = ""    //추후 개발 예정
    public var memo : String = ""


    public fun register(){

        val dto = PostPassengerDto(
            departure = departure!!,
            destination = destination!!,
            departureDate = departureDate!!,
            departureTime = departuretime!!,
            gift = gift,
            memo = memo
            )


        repository!!.requestSavePost(dto)
    }






}