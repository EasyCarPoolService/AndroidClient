package com.example.easycarpoolapp.fragment.post

import android.content.Context

class PostRepository private constructor(val context : Context){
    companion object{
        private var INSTANCE : PostRepository? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = PostRepository(context)
            }

        }//init
        fun getInstance() : PostRepository?{
            return INSTANCE?:
            throw Exception("Repository sould be initialized")
        }//getInstance()

        //해당 repository를 참조하는 클래스가 소멸될 경우 반드시 onDestroy를 호출
        fun onDestroy(){
            INSTANCE = null
        }//onDestroy
    }





    //=============================================================================================


}