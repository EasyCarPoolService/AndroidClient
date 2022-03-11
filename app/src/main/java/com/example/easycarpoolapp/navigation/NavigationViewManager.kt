package com.example.easycarpoolapp.navigation

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class NavigationViewManager (val context : Context, val binding : ActivityMainBinding) : NavigationView.OnNavigationItemSelectedListener{

    interface Callback{

        // MainActivity Callback
        fun onLogoutSelected()
        fun onLoginSelected()
        fun onProfileSelected()
        fun onProgressSelected()
    }


    private var callback : Callback? = null
    private lateinit var textLogState : TextView
    private lateinit var textLogout : TextView



    init{
        callback = context as Callback
        textLogState = binding.navView.getHeaderView(0).findViewById(R.id.navLogStateBtn)
        textLogout = binding.navView.getHeaderView(0).findViewById(R.id.text_logout)
    }

    public fun setNavView(){
        binding.navView.setNavigationItemSelectedListener(this) //navView하단 아이템 클릭 리스너 추가
        refreshView()


        textLogState.setOnClickListener {
            //만약 LocalUserData == null일경우 로그인 프래그먼트로 이동
            if(LocalUserData.getEmail() == null){
                callback?.onLoginSelected()
            }
        }   //로그인 시도  Hosting중 Activity에서 수행

        textLogout.setOnClickListener {
            LocalUserData.logout()
            refreshView()
        }//로그아웃 시도 Hosting중 Activity에서 수행

    }//setNavView

    private fun refreshView(){
        if(LocalUserData.getEmail() == null){   //로그아웃 되어있는 상태
            textLogout.visibility = View.GONE
            textLogState.text = "로그인 하기"
        }else{  //로그인 되어있는 상태
            textLogState.text = LocalUserData.getEmail()
            textLogout.visibility = View.VISIBLE
        }
    }   //로그인/로그아웃 상태에 따라 텍스트 및 버튼 상태 변경

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_test1 ->{
                callback?.onProfileSelected()
                return true
            }
            R.id.nav_test2->{
                callback?.onProgressSelected()
                return true
            }

        }
        return true
    }



}