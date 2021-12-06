package com.example.easycarpoolapp

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.example.easycarpoolapp.auth.AuthActivity
import com.example.easycarpoolapp.databinding.ActivityInfoBinding
import com.example.easycarpoolapp.databinding.ActivityMainBinding
import com.example.easycarpoolapp.fragment.LoginDialogFragment
import com.example.easycarpoolapp.fragment.home.HomeFragment
import com.example.easycarpoolapp.navigation.NavigationViewManager

class MainActivity : AppCompatActivity(), NavigationViewManager.Callback, LoginDialogFragment.Callbacks{

    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBar : ActionBar
    private lateinit var navigationViewManager: NavigationViewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        navigationViewManager = NavigationViewManager(this, binding)
        navigationViewManager.setNavView()
        setBottomNav()

        if(supportFragmentManager.findFragmentById(R.id.fragment_container)==null){
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment.getInstance()).commit()
        }
    }


    private fun setBottomNav(){


        binding.appBarMain.mainContentLayout.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.activity_main_bottom_nav_home->{
                    val fragment = HomeFragment.getInstance()
                    //supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
                    true
                }
                R.id.activity_main_bottom_nav_map->{
                    val fragment = HomeFragment.getInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    true
                }
                R.id.activity_main_bottom_nav_posts->{
                    val fragment = HomeFragment.getInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    true
                }
                R.id.activity_main_bottom_nav_chat->{
                    val fragment = HomeFragment.getInstance()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    true
                }

            }
            true
        }
    }
    private fun setActionBar(){
        setSupportActionBar(binding.appBarMain.toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayShowCustomEnabled(false)    //기존의 title지우기
        actionBar.setDisplayHomeAsUpEnabled(true)   //뒤로가기 버튼이 생기게 하기 위해 True설정
        actionBar.setHomeAsUpIndicator(R.drawable.icon_menu_delete)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{   //home키 - toolbar에 추가된 외쪽 상단의 버튼을 의미 따로 id를 지정하지 않음
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLogoutSelected() {}

    override fun onConfirmSelected() {
        startActivity(Intent(this, AuthActivity::class.java))
    }
}


