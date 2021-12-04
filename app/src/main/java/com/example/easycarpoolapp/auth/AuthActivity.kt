package com.example.easycarpoolapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.join.JoinFragment
import com.example.easycarpoolapp.databinding.ActivityAuthBinding
import com.example.easycarpoolapp.fragment.home.HomeFragment

class AuthActivity : AppCompatActivity(), AuthMainFragment.Callbacks {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        AuthRepository.init(this)   //Database에 접근하기 위한 Repository객체 생성메서드 호출

        if(supportFragmentManager.findFragmentById(binding.fragmentContainer.id)==null){
            supportFragmentManager.beginTransaction().add(binding.fragmentContainer.id, AuthMainFragment.getInstance()).commit()
        }




    }




    override fun onDestroy() {
        super.onDestroy()
        AuthRepository.onDestroy()
    }

    override fun onJoinSelected() {
        val fragment = JoinFragment.getInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
    }


}