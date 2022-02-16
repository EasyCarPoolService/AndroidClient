package com.example.easycarpoolapp.fragment.calendar

import android.content.Context
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.fragment.post.PostRepository

class CalendarRepository private constructor(val context : Context){

    private val TAG : String = "CalendarRepository"
    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"


    companion object{
        private var INSTANCE : CalendarRepository? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = CalendarRepository(context)
            }

        }//init
        fun getInstance() : CalendarRepository?{
            return INSTANCE?:
            throw Exception("Repository sould be initialized")
        }//getInstance()

        //해당 repository를 참조하는 클래스가 소멸될 경우 반드시 onDestroy를 호출
        fun onDestroy(){
            INSTANCE = null
        }//onDestroy
    }//companion object






}
