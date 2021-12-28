package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.ViewModel

class PostPassengerFormViewModel :ViewModel(){

    // PostPassengerFormFragment를 Hosting하는 Activity에서 PostRepository.init(context)수행
    private val repository : PostRepository? = PostRepository.getInstance()


    public var departure : String =""
    public var destination : String =""
    public var departureDate : String = ""
    public var departuretime : String = ""
    public var gift : String = "" //transform to arrayList or hashMap
    public var hashTag : String = ""
    public var memo : String = ""






}