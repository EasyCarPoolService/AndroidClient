package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.PostAPI
import com.example.easycarpoolapp.fragment.post.PostRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatRepository private constructor(val context : Context){


    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"

    companion object{
        private var INSTANCE : ChatRepository? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = ChatRepository(context)
            }

        }//init
        fun getInstance() : ChatRepository?{
            return INSTANCE?:
            throw Exception("Repository sould be initialized")
        }//getInstance()

        //해당 repository를 참조하는 클래스가 소멸될 경우 반드시 onDestroy를 호출
        fun onDestroy(){
            INSTANCE = null
        }//onDestroy
    }//companion object


    fun getChatRoom(dto : LocalUserDto, items : MutableLiveData<ArrayList<ChatRoomDto>>) {

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(ChatAPI::class.java)
        val call = api.getRoomCall(dto)

        call.enqueue(object : Callback<ArrayList<ChatRoomDto>>{
            override fun onResponse(
                call: Call<ArrayList<ChatRoomDto>>,
                response: Response<ArrayList<ChatRoomDto>>
            ) {
                items.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<ChatRoomDto>>, t: Throwable) {
                Log.e("GET ALL ROOM FAIL", t.message.toString())
            }

        })
    }

}