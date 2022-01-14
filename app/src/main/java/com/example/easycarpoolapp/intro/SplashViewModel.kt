package com.example.easycarpoolapp.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository

class SplashViewModel :ViewModel(){

    private val repository = AuthRepository.getInstance()
    public val tokenVerified : MutableLiveData<Boolean> = MutableLiveData()

    public fun verifyToken(){
        repository!!.getUserData(tokenVerified)


    }

}
