package com.example.easycarpoolapp.intro

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.MainActivity
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.AuthRepository

/*
Token 기반 인증 기능 추가 예정

현재
1 -> MainActivity 바로 이동
2 -> InfoActivity바로 이동
 */

@RequiresApi(Build.VERSION_CODES.M)
class SplashActivity : AppCompatActivity() {

    private val timeout : Long = 0 // 2 second
    private val PERMISSIONS=arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val viewModel : SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPermission()
        AuthRepository.init(context = this)

        moveToMain()    // delete

        setObserver()
        setUserData()

        //MainActivity로 이동
    }//onCreate

    override fun onDestroy() {
        super.onDestroy()
        AuthRepository.onDestroy()

    }//onDestroy

    private fun setObserver(){
        viewModel.tokenVerified.observe(this, Observer{
            if(it == true){
                moveToMain()
            }else{
                moveToInfo()
            }
        })

    }//setObserver


    private fun setUserData(){
        LocalUserData.createInstance()
        viewModel.verifyToken()

        //이하 repository -> database -> server token인증 수행

    }

    private fun moveToMain(){
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, timeout)
    }

    private fun moveToInfo(){
        Handler().postDelayed({
            startActivity(Intent(this, InfoActivity::class.java))
            finish()
        }, timeout)
    }

    private fun requestPermission() {
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS, 100
            )
        }
    }
}