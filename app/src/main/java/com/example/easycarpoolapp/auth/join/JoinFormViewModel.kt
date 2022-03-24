package com.example.easycarpoolapp.auth.join

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository

class JoinFormViewModel : ViewModel(){

    // AndroidAPIServer에 접근하기 위한 Repository
    private val authRepository : AuthRepository? = AuthRepository.getInstance()
    public var phoneNumber : String? = null
    public var transactionFlag : MutableLiveData<String> = MutableLiveData() //transaction결과 -> email

    public fun join(profile_image : Bitmap?, name : String, email:String, nickname:String, password : String, birth:String, gender:String, fcmToken : String){
        authRepository?.signUp(profile_image = profile_image, name, email, nickname, password, birth, gender, fcmToken, transactionFlag)
    }




}