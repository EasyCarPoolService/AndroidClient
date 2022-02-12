package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.fragment.post.dto.PostDriverDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto

class PostDriverFormViewModel : ViewModel(){

    private val repository : PostRepository? = PostRepository.getInstance()

    public var departure : String? = null
    public var destination : String? = null
    public var departureDate : String? = null
    public var departuretime : String? = null
    public var gift : ArrayList<String> = ArrayList() //transform to arrayList or hashMap
    public var hashTag : String = ""    //추후 개발 예정
    public var message : String = ""


    public fun register(){

        val dto = PostDriverDto(
            type = "driver",
            email = LocalUserData.getEmail()!!,
            nickname = LocalUserData.getNickname()!!,
            gender = LocalUserData.getGender()!!,
            departure = departure!!,
            destination = destination!!,
            departureDate = departureDate!!,
            departureTime = departuretime!!,
            gift = giftToString(),
            message = message!!,
            fcmToken = LocalUserData.getFcmToken()!!
        )
        repository!!.requestSaveDriverPost(dto)
    }//register


    private fun giftToString() : String{
        var result : String=""
        for(item in gift){
            result+=item+","
        }
        return result
    }//giftToSrtring

}