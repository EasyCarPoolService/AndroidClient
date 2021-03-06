package com.example.easycarpoolapp.auth

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.OKHttpHelper
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.auth.dto.LoginDto
import com.example.easycarpoolapp.utils.ImageFileManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
import java.util.concurrent.TimeUnit

class AuthRepository private constructor(val context : Context){


    companion object{
        private var INSTANCE: AuthRepository?=null   //Singleton패턴을 위한 INSTANCE변수
        private lateinit var imageFileManager: ImageFileManager

        fun init(context: Context){
            if(INSTANCE ==null){
                imageFileManager = ImageFileManager(context)
                INSTANCE = AuthRepository(context)   //MainActivity가 onCreate될경우 호출
            }

        }
        fun getInstance() : AuthRepository?{  //ViewModel에서 객체를 얻기위해 호출
            return INSTANCE ?:
            throw Exception("Repository sould be initialized")
        }

        //해당 repository를 참조하는 클래스가 소멸될 경우 반드시 onDestroy를 호출
        fun onDestroy(){
            INSTANCE = null
        }
    }

    //=============================================================================================

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.e("ONVERIFICATION SUCCESS", p0.toString())
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.e("ONVERIFICATION FAIL", p0.toString())
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            verificationId = p0
        }
    }

    //=============================================================================================
    //variable

    private lateinit var verificationId : String
    private val BASEURL :String = "http://"+NetworkConfig.getIP()+":8080"

    //=============================================================================================

    public fun sendCode(phoneNum : String){

        //robots 검사 생략
        //Firebase.auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        val number = "+82"+phoneNum.substring(1)

        Log.e("NUMBER IS", number.toString())

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(120, TimeUnit.SECONDS)
            .setCallbacks(callbacks)
            .setActivity(context as Activity)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //=============================================================================================

    public fun verifyPhone(
        code: String,
        phoneNumber: String?,
        verificationResult: MutableLiveData<Boolean>
    ){
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        credential.smsCode
        credential.provider

        val firebaseAuthSettings = Firebase.auth.firebaseAuthSettings
        val number = "+82"+phoneNumber?.substring(1)
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(number, code)

        Firebase.auth.signInWithCredential(credential!!)
            .addOnSuccessListener {
                verificationResult.value = true
            }
            .addOnFailureListener {
                verificationResult.value = false
            }
    }

    //=============================================================================================

    //회원가입의 경우 driverAuthentication은 항상 false
    public fun signUp(
        profile_image: Bitmap?,
        name: String,
        email: String,
        nickname: String,
        password: String,
        birth: String,
        gender: String,
        fcmToken: String,
        transactionFlag: MutableLiveData<String>
    ){

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        var profile_image_body : MultipartBody.Part? = null

        Log.e("REPOSITORY", fcmToken)

        //image 내부 저장소에 저장
        if(profile_image!=null){
            val profileImageFile : File = imageFileManager.createImageFile(profile_image)
            val fileName : String = email+"_profile.jpg" //서버에 저장되는 파일명
            //val fileName : String = "testImage.jpg" //서버에 저장되는 파일명

            //fileManager.getFile() -> 미리 지정해둔 특정 파일 하나에 해당
            var requestBody_image : RequestBody = RequestBody.create(MediaType.parse("image/*"), profileImageFile)

            //createFoemData에 지정한 name -> (Spring Boot) files.getName() 메서드로 얻는 이름
            //fileName 변수에 저장되어있는 문자열 -> Server에 저장되는 파일명(확장자포함)
            //requestBody -> imageFile에 해당
            profile_image_body = MultipartBody.Part.createFormData("profile_image", fileName, requestBody_image)
        }

        val name_body = RequestBody.create(MediaType.parse("text/plain"), name)
        val email_body = RequestBody.create(MediaType.parse("text/plain"), email)
        val nickname_body = RequestBody.create(MediaType.parse("text/plain"), nickname)
        val password_body = RequestBody.create(MediaType.parse("text/plain"), password)
        val birth_body = RequestBody.create(MediaType.parse("text/plain"), birth)
        val gender_body = RequestBody.create(MediaType.parse("text/plain"), gender)
        val fcmToken_body = RequestBody.create(MediaType.parse("text/plain"), fcmToken)


        val api = retrofit.create(AuthAPI::class.java)
        val call = api.getSignUpCall(
            profile_image_body,
            name = name_body,
            email = email_body,
            nickname = nickname_body,
            password = password_body,
            birth = birth_body,
            gender = gender_body,
            fcmToken = fcmToken_body
            )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val body = response.body()
                if(body!= null){
                    transactionFlag.value = body
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("RESPONSE FAIL", t.message.toString())
            }
        })
    }

    //=============================================================================================

    public fun login(loginDto :LoginDto, loginFlag : MutableLiveData<Boolean>){
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(AuthAPI::class.java)
        val call = api.getLoginCall(loginDto = loginDto)

        call.enqueue(object : Callback<LocalUserDto>{
            override fun onResponse(call: Call<LocalUserDto>, response: Response<LocalUserDto>) {
                // android sharedpreference에 토큰 저장 -> 서버로 부터 받은 값이 토큰이 맞다면 저장하도록 유도
                saveTokenSharedPreference(response.body()?.token.toString())
                val body = response.body()
                //Single패턴으로 유지될 UserLocalData 객체에 userData set 수행
                if(body != null) {
                    saveTokenSharedPreference(body.token!!)
                    setLocalUserData(body!!)
                    loginFlag.value = true
                }else{
                    loginFlag.value = false
                }
            }
            override fun onFailure(call: Call<LocalUserDto>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
                loginFlag.value = false
            }

        })

    }//login

    //=============================================================================================

    // SplashActivity에서 수행 -> 토큰을 검증하고 서버로부터 사용자 정보를 응답 받아 싱글턴 객체 구성
    public fun getUserData(tokenVerifyed : MutableLiveData<Boolean>){

        Log.e("AuthRepository", "getUserData Called")

        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(OKHttpHelper.createGson()))
            .client(OKHttpHelper.createHttpClient(context))     //내부에 저장되어있는 코튼을 꺼내 client생성
            .build()
        val api = retrofit.create(AuthAPI::class.java)
        val call = api.getUserDataCall()

        call.enqueue(object : Callback<LocalUserDto>{
            override fun onResponse(call: Call<LocalUserDto>, response: Response<LocalUserDto>) {
                val body = response.body()
                //Single패턴으로 유지될 UserLocalData 객체에 userData set 수행

                if(body != null) {
                    setLocalUserData(body!!)
                    tokenVerifyed.value = true  //인증성공
                }else{
                    tokenVerifyed.value = false //인증 실패
                }
            }

            override fun onFailure(call: Call<LocalUserDto>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
                tokenVerifyed.value = false // 인증 실패
            }

        })
    }//getUserData

    //splashActivity에서 토큰 인증 후 결과로 얻은 유저정보 set
    private fun setLocalUserData(body : LocalUserDto) = LocalUserData.login(
        _token = body.token,
        _email = body.email,
        _nickname = body.nickname,
        _gender = body.gender,
        _rate = body.rate,
        _driverAuthentication = body.driverAuthentication,
        _fcmToken = body.fcmToken
        )//setLocalUserData

    //=============================================================================================
    private fun saveTokenSharedPreference(token : String){
        val sharedPreference = context.getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("token", token)
        //commit 의 경우 동기적으로 수행 되기에 write를 수행하는 크기가 크다면 UI coroutine 등을 통해 비 동기적으로 수행할것을 권장
        editor.commit()
    }//saveTokenSharedPreference


}