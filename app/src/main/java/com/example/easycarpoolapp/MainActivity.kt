package com.example.easycarpoolapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.databinding.ActivityInfoBinding
import com.example.easycarpoolapp.databinding.ActivityMainBinding
import com.example.easycarpoolapp.navigation.NavigationViewManager

class MainActivity : AppCompatActivity(), NavigationViewManager.Callback {

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



    }


    private fun setBottomNav(){
        binding.appBarMain.mainContentLayout.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.activity_main_bottom_nav_item1->{
                    Toast.makeText(this, "prototype fragment1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.activity_main_bottom_nav_item2->{
                    Toast.makeText(this, "prototype fragment2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.activity_main_bottom_nav_item3->{
                    Toast.makeText(this, "prototype fragment3", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.activity_main_bottom_nav_item4->{
                    Toast.makeText(this, "prototype fragment4", Toast.LENGTH_SHORT).show()
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

    override fun onLogoutSelected() {
        TODO("Not yet implemented")
    }
}


