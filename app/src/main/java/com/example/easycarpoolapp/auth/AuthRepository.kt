package com.example.easycarpoolapp.auth

import android.content.Context
import kotlin.reflect.KParameter

class AuthRepository private constructor(context : Context){

    companion object{
        private var INSTANCE: AuthRepository?=null   //Singleton패턴을 위한 INSTANCE변수

        fun init(context: Context){
            if(INSTANCE ==null){
                INSTANCE = AuthRepository(context)   //MainActivity가 onCreate될경우 호출
            }

        }
        fun getInstance() : AuthRepository?{  //ViewModel에서 객체를 얻기위해 호출
            return INSTANCE ?:
            throw Exception("Repository sould be initialized")
        }

        fun onDestroy(){
            INSTANCE = null
        }



    }



}