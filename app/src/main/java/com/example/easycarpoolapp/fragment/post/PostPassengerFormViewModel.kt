package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
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
    public var message : String = ""
    public val transactionFlag : MutableLiveData<String> = MutableLiveData()


    public fun register(){

        val dto = PostPassengerDto(
            type = "passenger",
            email = LocalUserData.getEmail()!!,
            nickname = LocalUserData.getNickname()!!,
            gender = LocalUserData.getGender()!!,
            rate = LocalUserData.getRate()!!,
            departure = departure!!,
            destination = destination!!,
            departureDate = departureDate!!,
            departureTime = departuretime!!,
            gift = giftToString(),
            message = message!!,
            fcmToken = LocalUserData.getFcmToken()!!
            )
        repository!!.requestSavePassengerPost(dto, transactionFlag)
    }//register

    private fun giftToString() : String{
        var result : String=""
        for(item in gift){
            result+=item+","
        }
        return result
    }//giftToString()


}