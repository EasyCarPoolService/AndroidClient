package com.example.easycarpoolapp.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.easycarpoolapp.MainActivity
import com.example.easycarpoolapp.R

/*
Token 기반 인증 기능 추가 예정

현재
1 -> MainActivity 바로 이동
2 -> InfoActivity바로 이동
 */


class SplashActivity : AppCompatActivity() {

    private val timeout : Long = 2000 // 2 second


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
}