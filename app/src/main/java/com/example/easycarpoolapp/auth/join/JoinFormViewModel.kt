package com.example.easycarpoolapp.auth.join

import android.app.AuthenticationRequiredException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository
import com.example.easycarpoolapp.auth.dto.JoinDto

class JoinFormViewModel : ViewModel(){

    // AndroidAPIServer에 접근하기 위한 Repository
    private val authRepository : AuthRepository? = AuthRepository.getInstance()
    public var phoneNumber : String? = null

    public fun join(joinDto : JoinDto){
        authRepository?.signUp(joinDto)
    }


}