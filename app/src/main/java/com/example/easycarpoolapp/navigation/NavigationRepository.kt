package com.example.easycarpoolapp.navigation

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.utils.ImageFileManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NavigationRepository private constructor(val context : Context){


    companion object{
        private var INSTANCE: NavigationRepository? = null
        private var imageFileManager : ImageFileManager? = null

        fun init(context : Context){
            if(INSTANCE == null){
                INSTANCE = NavigationRepository(context)
            }
            imageFileManager = ImageFileManager(context)

        }// init

        fun getInstance() : NavigationRepository?{
            return INSTANCE ?:
            throw Exception("Repository sould be initialized")
        } // gektInstance

        fun onDestroy(){
            INSTANCE = null
        } // onDestroy
    }//companion object

    private val BASEURL :String = "http://"+ NetworkConfig.getIP()+":8080"
    private val TAG : String = "NavigationRepository"


    public fun authenticateDriver(bitmapId : Bitmap, bitmapCar : Bitmap){

        //retrofit갤러리로 부터 선택한 비트맵을 파일형태로 특정 경로에 저장 -> retrofit을 통해 이미지를 업로드할때 사용
        val idImagePath : String =imageFileManager!!.createImageFile(bitmapId)
        val carImagePath : String = imageFileManager!!.createImageFile(bitmapCar)

        val idImageFileName : String = LocalUserData.getEmail()+"_Id.jpg" //서버에 저장되는 파일명
        val carImageFileNmae : String  = LocalUserData.getEmail()+"_Car.jpg"

        var requestBody_id : RequestBody = RequestBody.create(MediaType.parse("image/*"), idImagePath)
        var requestBody_car : RequestBody = RequestBody.create(MediaType.parse("image/*"), carImagePath)

        //createFoemData에 지정한 name -> (Spring Boot) files.getName() 메서드로 얻는 이름
        //fileName 변수에 저장되어있는 문자열 -> Server에 저장되는 파일명(확장자포함)
        //requestBody -> imageFile에 해당
        var body_id : MultipartBody.Part = MultipartBody.Part.createFormData("idImage", idImageFileName, requestBody_id)
        var body_car : MultipartBody.Part = MultipartBody.Part.createFormData("carImage", carImageFileNmae, requestBody_car)

        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.getDriverAuthCall(body_id, body_car)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e(TAG, response.body().toString())
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }//authenticateDriver









}