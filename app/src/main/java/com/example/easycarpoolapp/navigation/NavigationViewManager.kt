package com.example.easycarpoolapp.navigation

import android.content.Context
import android.content.Intent
import android.service.autofill.UserData
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class NavigationViewManager (val context : Context, val binding : ActivityMainBinding) : NavigationView.OnNavigationItemSelectedListener{

    interface Callback{
        fun onLogoutSelected()
    }


    private var callback : Callback? = null

    init{
        callback = context as Callback
    }

    public fun setNavView(){

        val btnLogState : TextView = binding.navView.getHeaderView(0).findViewById(R.id.navLogStateBtn)
        binding.navView.setNavigationItemSelectedListener(this)

        btnLogState.setOnClickListener {
            Toast.makeText(context, "logout action prototype", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_test1 ->{
                Toast.makeText(context, "prototype1", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.nav_test2->{
                Toast.makeText(context, "prototype1", Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return true
    }



}