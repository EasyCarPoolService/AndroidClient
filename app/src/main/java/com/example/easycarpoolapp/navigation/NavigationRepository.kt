package com.example.easycarpoolapp.navigation

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.post.PostAPI
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto
import com.example.easycarpoolapp.navigation.profile.report.dto.AccuseDto
import com.example.easycarpoolapp.navigation.profile.report.dto.ReportDto
import com.example.easycarpoolapp.navigation.progress.dto.PostReviewDto
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
import java.io.File

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



    public fun authenticateDriver(bitmapId : Bitmap, bitmapCar : Bitmap, carNumber : String, manufacturer : String, model : String){

        //retrofit갤러리로 부터 선택한 비트맵을 파일형태로 특정 경로에 저장 -> retrofit을 통해 이미지를 업로드할때 사용
        val idImageFile : File =imageFileManager!!.createImageFile(bitmapId)
        val carImageFile : File = imageFileManager!!.createImageFile(bitmapCar)

        val idImageFileName : String = LocalUserData.getEmail()+"_Id.jpg" //서버에 저장되는 파일명
        val carImageFileNmae : String  = LocalUserData.getEmail()+"_Car.jpg"

        var requestBody_id : RequestBody = RequestBody.create(MediaType.parse("image/*"), idImageFile)
        var requestBody_car : RequestBody = RequestBody.create(MediaType.parse("image/*"), carImageFile)

        //createFoemData에 지정한 name -> (Spring Boot) files.getName() 메서드로 얻는 이름
        //fileName 변수에 저장되어있는 문자열 -> Server에 저장되는 파일명(확장자포함)
        //requestBody -> imageFile에 해당
        var body_id : MultipartBody.Part = MultipartBody.Part.createFormData("idImage", idImageFileName, requestBody_id)
        var body_car : MultipartBody.Part = MultipartBody.Part.createFormData("carImage", carImageFileNmae, requestBody_car)


        //car info & user Email
        val email_body = RequestBody.create(MediaType.parse("text/plain"), LocalUserData.getEmail())
        val carNumber_body = RequestBody.create(MediaType.parse("text/plain"), carNumber)
        val manufacturer_body = RequestBody.create(MediaType.parse("text/plain"), manufacturer)
        val model_body = RequestBody.create(MediaType.parse("text/plain"), model)


        //retrofit
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.getDriverAuthCall(body_id, body_car, email_body ,carNumber_body, manufacturer_body, model_body)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e(TAG, response.body().toString())
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }//authenticateDriver



    //이하 profile Fragment 관련 메서드
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

    fun editProfile(
        profile_image: Bitmap,
        nickname: String,
        gender: String,
        introduce_message: String,
        transaction_flag: MutableLiveData<String>
    ) {

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        var profile_image_body : MultipartBody.Part? = null
        if(profile_image!=null){
            val profileImageFile : File = imageFileManager!!.createImageFile(profile_image)
            val fileName : String = LocalUserData.getEmail()+"_profile.jpg" //서버에 저장되는 파일명
            //val fileName : String = "testImage.jpg" //서버에 저장되는 파일명

            //fileManager.getFile() -> 미리 지정해둔 특정 파일 하나에 해당
            var requestBody_image : RequestBody = RequestBody.create(MediaType.parse("image/*"), profileImageFile)

            //createFoemData에 지정한 name -> (Spring Boot) files.getName() 메서드로 얻는 이름
            //fileName 변수에 저장되어있는 문자열 -> Server에 저장되는 파일명(확장자포함)
            //requestBody -> imageFile에 해당
            profile_image_body = MultipartBody.Part.createFormData("profile_image", fileName, requestBody_image)
        }

        val email_body = RequestBody.create(MediaType.parse("text/plain"), LocalUserData.getEmail())
        val nickname_body = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val gender_body = RequestBody.create(MediaType.parse("text/plain"), gender)
        val introduce_message = RequestBody.create(MediaType.parse("text/plain"), introduce_message)

        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.getEditProfileCall(
            email_body,
            profile_image_body,
            nickname_body,
            gender_body,
            introduce_message
        )


        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body != null){
                    transaction_flag.value = body
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }// editProfile()

    fun progressToComplete(dto : PostReviewDto, completeFlag : MutableLiveData<Boolean>) {

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()


        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.progressToComplete(dto)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if (body != null && body.equals("success")) {
                    completeFlag.value = true   //ProgressDetailFragment에서 처리
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })


    } //progressToComplete

    fun accuseUser(accuseDto: AccuseDto, transaction_flag: MutableLiveData<String>) {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.getAccuseCall(accuseDto)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body != null){
                    transaction_flag.value = body
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })


    }// accuseUser()

    fun reportAdmin(reportDto: ReportDto, transaction_flag: MutableLiveData<String>) {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OKHttpHelper.createHttpClient(context))
            .build()

        val api = retrofit.create(NavigationAPI::class.java)
        val call = api.getReportCall(reportDto)

        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body != null){
                    transaction_flag.value = body
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

        })

    }// reportAdmin()


}