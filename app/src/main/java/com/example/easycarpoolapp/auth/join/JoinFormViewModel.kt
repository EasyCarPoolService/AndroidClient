package com.example.easycarpoolapp.auth.join

import android.app.AuthenticationRequiredException
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository
import com.example.easycarpoolapp.auth.dto.JoinDto

class JoinFormViewModel : ViewModel(){

    // AndroidAPIServer에 접근하기 위한 Repository
    private val authRepository : AuthRepository? = AuthRepository.getInstance()
    public var phoneNumber : String? = null


    //기존 코드
    /*public fun join(joinDto : JoinDto){
        authRepository?.signUp(joinDto)
    }*/


    public fun join(profile_image : Bitmap?, name : String, email:String, nickname:String, password : String, birth:String, gender:String, fcmToken : String){
        authRepository?.signUp(profile_image = profile_image, name, email, nickname, password, birth, gender, fcmToken)
    }


}