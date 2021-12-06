package com.example.easycarpoolapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.join.JoinFormFragment
import com.example.easycarpoolapp.auth.join.JoinPhoneFragment
import com.example.easycarpoolapp.auth.join.JoinPhoneVerifyFragment
import com.example.easycarpoolapp.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity(), AuthMainFragment.Callbacks, JoinPhoneFragment.Callbacks, JoinPhoneVerifyFragment.CallBacks{

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
        val fragment = JoinPhoneFragment.getInstance()
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }

    override fun onNextSelected() {
        val fragment = JoinPhoneVerifyFragment.getInstance()
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }

    override fun afterVerified() {
        val fragment = JoinFormFragment.getInstance()
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }


}