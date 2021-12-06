package com.example.easycarpoolapp.auth.join

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository

class JoinPhoneVerifyViewModel : ViewModel(){
    private val repository = AuthRepository.getInstance()
    public val code : MutableLiveData<String> = MutableLiveData()
    public val verificationResult : MutableLiveData<Boolean> = MutableLiveData()


    public fun verify(){
        repository?.verify(code.value!!, verificationResult)
    }

}