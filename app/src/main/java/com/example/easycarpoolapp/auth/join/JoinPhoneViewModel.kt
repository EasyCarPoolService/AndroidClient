package com.example.easycarpoolapp.auth.join

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository

class JoinPhoneViewModel : ViewModel(){

    private val repository : AuthRepository? = AuthRepository.getInstance()
    public val phoneNumber : MutableLiveData<String> = MutableLiveData()

    public fun sendCode(){
        phoneNumber.value?.let { repository?.sendCode(it) }
    }


}