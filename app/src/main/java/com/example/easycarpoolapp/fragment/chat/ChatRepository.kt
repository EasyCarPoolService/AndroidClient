package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class ChatRepository private constructor(val context : Context){


    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"


    private lateinit var stompClient : StompClient

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


    public fun subscribe(roomId: String, chatList: MutableLiveData<java.util.ArrayList<ChatDto>>) {
        // STOMP전용 URL설정 필요 여부 판단
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, NetworkConfig.getSocketURL())

        stompClient.connect()
        stompClient.topic("/sub/chat/room"+roomId).subscribe{

            val jsonObj = JSONObject(it.payload)
            val message = jsonObj.getString("message")
            val time= jsonObj.getString("time")
            val writer = jsonObj.getString("writer")
            val dto = ChatDto(roomId=roomId, writer = writer ,message = message, time = time)
            var temp = chatList.value
            temp!!.add(dto)
            chatList.postValue(temp)
        }
    }//subscribe

    fun sendMessage(roomId: String, email: String?, message: String, fcmToken : String) {
        val data = JSONObject().apply {
            put("roomId", roomId)
            put("writer", email)
            put("message", message)
            put("fcmToken", fcmToken)
        }

        stompClient.send("/app/chat/message", data.toString()).subscribe()

    }//sendMessage


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
    }//getChatRoom

    fun findMessageByRoomId(roomId: String, chatList: MutableLiveData<java.util.ArrayList<ChatDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(ChatAPI::class.java)
        val call = api.getFindMessageCall(ChatRoomDto(roomId = roomId))
        call.enqueue(object :Callback<ArrayList<ChatDto>>{
            override fun onResponse(
                call: Call<ArrayList<ChatDto>>,
                response: Response<ArrayList<ChatDto>>
            ) {
                Log.e("VALUE!!", response.body().toString())
                chatList.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<ChatDto>>, t: Throwable) {
                Log.e("FindMessage FAIL!!", t.message.toString())
            }

        })


    }//findMessageByRoomId

}