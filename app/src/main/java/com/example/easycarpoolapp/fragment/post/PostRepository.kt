package com.example.easycarpoolapp.fragment.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.PostDriverDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList


//PostHomeFragment 에서 init수행
class PostRepository private constructor(val context : Context){
    private val TAG : String = "PostRepository"
    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"


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
    }//companion object

    //=============================================================================================

    public fun getDriverAuth(){

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getDriverAuthCall(LocalUserData.getEmail())


    }



    //=============================================================================================

    public fun requestSaveDriverPost(dto: PostDriverDto) {
        Log.e(TAG, dto.toString())

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getDriverSaveCall(postDriverDto = dto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e(TAG, response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    } // requestSavePassengerPost

    //=============================================================================================

    public fun requestSavePassengerPost(dto: PostPassengerDto) {
        Log.e(TAG, dto.toString())

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getPassengerSaveCall(postPassengerDto = dto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e(TAG, response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    } // requestSavePassengerPost

    fun getPassengerPost(postItems: MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getPassengerPostCall()

        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                postItems.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//getPassengerPost


    fun getDriverPost(postItems: MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getDriverPostCall()

        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                postItems.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//getDrvierPost


    //check
    fun createChatRoom(dto : ChatRoomDto, roomInfo: MutableLiveData<ChatRoomDto>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getCreateRoomCall(chatRoomDto = dto)

        call.enqueue(object : Callback<ChatRoomDto>{
            override fun onResponse(call: Call<ChatRoomDto>, response: Response<ChatRoomDto>) {

                roomInfo.value = response.body()!!
            }

            override fun onFailure(call: Call<ChatRoomDto>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//createChatRoom
}