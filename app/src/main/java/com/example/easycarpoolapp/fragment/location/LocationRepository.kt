package com.example.easycarpoolapp.fragment.location

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.calendar.CalendarAPI
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.location.dto.LocationDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient

class LocationRepository private constructor(val context : Context){

    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"

    private val TAG : String = "LocationRepository"
    private lateinit var stompClient : StompClient

    companion object{
        private var INSTANCE : LocationRepository? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = LocationRepository(context)
            }
        }//init

        fun getInstance() : LocationRepository? {
            return INSTANCE?:
            throw Exception("Repository should be initialized")
        }//getInstance()

        fun onDestroy(){
            INSTANCE = null
        }//onDestroy

    }//companion object

    fun getReservedPost(items: MutableLiveData<ArrayList<ReservedPostDto>>, email: String?) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(LocationAPI::class.java)
        val call = api.getReservedPostCall(LocalUserDto(email = LocalUserData.getEmail()))
        call.enqueue(object : Callback<ArrayList<ReservedPostDto>>{
            override fun onResponse(
                call: Call<ArrayList<ReservedPostDto>>,
                response: Response<ArrayList<ReservedPostDto>>
            ) {
                val body = response.body()
                if(body!=null){
                    Log.e(TAG, "success!!")
                    items.value = body
                }

            }

            override fun onFailure(call: Call<ArrayList<ReservedPostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//getReservedPost()

    fun subscribe(roomId: String, opponentLocation: MutableLiveData<LocationDto>) {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, NetworkConfig.getSocketURL())
        stompClient.connect()

        stompClient.topic("/sub/location/room"+roomId).subscribe{

            val jsonObj = JSONObject(it.payload)
            val writer = jsonObj.getString("writer")
            val lat = jsonObj.getDouble("lat")
            val lon = jsonObj.getDouble("lon")
            val dto = LocationDto(writer = writer, lat = lat, lon = lon)
            opponentLocation.postValue(dto)
        }

    }//subscribe

    fun sendLocation(roomId : String, location : Location){
        val data = JSONObject().apply {
            put("roomId", roomId)
            put("writer", LocalUserData.getEmail().toString())
            put("lat", location.latitude)
            put("lon", location.longitude)
        }

        stompClient.send("/app/location/stomp", data.toString()).subscribe()
    }//sendLocation


}