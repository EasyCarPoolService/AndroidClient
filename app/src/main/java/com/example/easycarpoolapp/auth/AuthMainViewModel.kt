package com.example.easycarpoolapp.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.dto.LoginDto
import java.lang.Exception

class AuthMainViewModel : ViewModel() {
    private val authRepository : AuthRepository? = AuthRepository.getInstance()
    public var email : String? = null
    public var password : String? = null


    public fun login(){
        if(email != null && password !=null){

            val loginDto : LoginDto = LoginDto(email = email!!, password = password!!)
            try{
                authRepository?.login(loginDto)
            }catch (e : Exception){
                Log.e("viewmodel", "catch!!")
            }

        }
    }

}