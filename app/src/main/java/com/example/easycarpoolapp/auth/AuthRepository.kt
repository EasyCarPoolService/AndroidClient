package com.example.easycarpoolapp.auth

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.easycarpoolapp.auth.domain.User
import com.example.easycarpoolapp.auth.dto.JoinDto
import com.example.easycarpoolapp.auth.dto.LoginDto
import com.example.easycarpoolapp.auth.dto.TokenDto
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KParameter

class AuthRepository private constructor(val context : Context){


    companion object{
        private var INSTANCE: AuthRepository?=null   //Singleton패턴을 위한 INSTANCE변수

        fun init(context: Context){
            if(INSTANCE ==null){
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
    private val BASEURL :String = "http://192.168.45.182:8080"


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

    public fun verify(
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

    public fun signUp(joinDto : JoinDto){
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(AuthAPI::class.java)
        val call = api.getSignUpCall(joinDto = joinDto)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("RESPONSE", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("RESPONSE FAIL", t.message.toString())
            }
        })
    }
    //=============================================================================================
    public fun login(loginDto :LoginDto){
        val retrofit = Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(AuthAPI::class.java)
        val call = api.getLoginCall(loginDto = loginDto)
        call.enqueue(object : Callback<TokenDto>{
            override fun onResponse(call: Call<TokenDto>, response: Response<TokenDto>) {
                //내부 데이터베이스에 토큰정보 저장 -> 만약 서버로부터 받은 값이 토큰이 맞다면 수행하도록 로직 변경!
                saveTokenInternalDatabase(response.body()?.token.toString())
            }
            override fun onFailure(call: Call<TokenDto>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }

    //=============================================================================================
    //내부 데이터베이스에 인증을 수행할 토큰만 저장
    private fun saveTokenInternalDatabase(token:String?){

        val parkingAppDBHelper = AuthDBHelper(context)
        val sqlWrite = parkingAppDBHelper.writableDatabase
        val sqlRead = parkingAppDBHelper.readableDatabase
        var cursor: Cursor?=null


        sqlWrite!!.execSQL("CREATE TABLE IF NOT EXISTS userTBL(item varchar(10), token text);")
        cursor=sqlRead!!.rawQuery("SELECT COUNT(*) FROM userTBL", null)
        cursor!!.moveToNext()
        var numberOfData = cursor!!.getString(0).toString()
        if(numberOfData.equals("0")){
            sqlWrite!!.execSQL("INSERT INTO userTBL VALUES('${token}', '${token}');")
            Log.e("INSERT INTO OPERATE", token.toString())
        }else{
            Log.e("UPDATE CALLED", token.toString())
            sqlWrite!!.execSQL("UPDATE userTBL SET uuid='${token}';")    //내부 데이터베이스에 서버로 부터 받은 uuid저장 -> splash에서 서버로 유효성 검사 수행
            sqlWrite!!.execSQL("UPDATE userTBL SET token='${token}';")
            //내부 데이터베이스에 서버로 부터 받은 uuid저장 -> splash에서 서버로 유효성 검사 수행
        }


        //이하 database access 객체 모두 close
        parkingAppDBHelper.close()
        sqlWrite.close()
        sqlRead.close()
        cursor.close()

    }// saveTokenInternalDatabase


    //=============================================================================================

}