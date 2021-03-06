package com.example.easycarpoolapp.fragment.post

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    //mutableLiveData 추가할것
    public fun getDriverAuth(){

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)

        val call = api.getDriverAuthCall(LocalUserDto(email = LocalUserData.getEmail()))
        call.enqueue(object : Callback<LocalUserDto>{
            override fun onResponse(call: Call<LocalUserDto>, response: Response<LocalUserDto>) {
                val body = response.body()
                if(body != null) {
                    Log.e("TAG", body.driverAuthentication.toString())
                    LocalUserData.updateDriverAuthentication(body.driverAuthentication!!)
                }
            }

            override fun onFailure(call: Call<LocalUserDto>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }

        })

    }//getDriverAuth()


    //=============================================================================================
    /*private fun setLocalUserData(body : LocalUserDto) = LocalUserData.login(
        _token = body.token,
        _email = body.email,
        _nickname = body.nickname,
        _gender = body.gender,
        _driverAuthentication = body.driverAuthentication,
        _fcmToken = body.fcmToken
    )//setLocalUserData*/


    //=============================================================================================

    public fun requestSaveDriverPost(dto: PostDriverDto, transactionFlag: MutableLiveData<String>) {
        Log.e(TAG, dto.toString())

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getDriverSaveCall(postDriverDto = dto)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body !=null){
                    transactionFlag.value = body
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    } // requestSavePassengerPost

    //=============================================================================================

    public fun requestSavePassengerPost(
        dto: PostPassengerDto,
        transactionFlag: MutableLiveData<String>
    ) {
        Log.e(TAG, dto.toString())

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getPassengerSaveCall(postPassengerDto = dto)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body != null){
                    transactionFlag.value = body
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    } // requestSavePassengerPost

    fun getPassengerPost(currentPage : Int, postItems: MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getPassengerPostCall(currentPage = currentPage)

        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {

                val body = response.body()
                if(body != null){
                    postItems.value = response.body()
                }

            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//getPassengerPost


    fun getDriverPost(currentPage : Int, postItems: MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getDriverPostCall(currentPage = currentPage)

        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                val body = response.body()
                if(body != null){
                    postItems.value = response.body()
                }

            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }//getDrvierPost


    //User가 작성한 게시글 조회하여 RecyclerView에 띄우기
    fun getUserPost(postItems: MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getUserPostCall(LocalUserDto(email = LocalUserData.getEmail()))

        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                val body = response.body()
                if(body != null){
                    postItems.value = body
                }

            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })
    }//getUserPost


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

    fun getUserPostData(userPostDto: MutableLiveData<UserPostDto>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(PostAPI::class.java)
        val call = api.getUserPostDataCall(LocalUserDto(email = LocalUserData.getEmail()))

        call.enqueue(object : Callback<UserPostDto> {
            override fun onResponse(call: Call<UserPostDto>, response: Response<UserPostDto>) {
                val body = response.body()

                if(body != null){
                    Log.e(TAG, body.driver.toString())
                    userPostDto.value = body
                }
            }
            override fun onFailure(call: Call<UserPostDto>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })



    }//getUserPostData

    fun getPostByDistrict(postDistrictDto: PostDistrictDto, items : MutableLiveData<ArrayList<PostDto>>) {
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OKHttpHelper.createHttpClient(context))
            .build()


        val api = retrofit.create(PostAPI::class.java)
        val call = api.getPostByDistrictCall(postDistrictDto)
        call.enqueue(object : Callback<ArrayList<PostDto>>{
            override fun onResponse(
                call: Call<ArrayList<PostDto>>,
                response: Response<ArrayList<PostDto>>
            ) {
                val body = response.body()
                if(body !=null){
                    items.value = body
                }
            }

            override fun onFailure(call: Call<ArrayList<PostDto>>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })



    }// 지역명으로 게시글 찾기



}