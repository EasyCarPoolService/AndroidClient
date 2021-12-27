package com.example.easycarpoolapp.intro

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.easycarpoolapp.MainActivity
import com.example.easycarpoolapp.R

/*
Token 기반 인증 기능 추가 예정

현재
1 -> MainActivity 바로 이동
2 -> InfoActivity바로 이동
 */

@RequiresApi(Build.VERSION_CODES.M)
class SplashActivity : AppCompatActivity() {

    private val timeout : Long = 2000 // 2 second
    private val PERMISSIONS=arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestPermission()
        moveToInfo()

        //MainActivity로 이동
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