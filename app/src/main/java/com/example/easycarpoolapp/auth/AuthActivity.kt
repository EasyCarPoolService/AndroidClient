package com.example.easycarpoolapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.MainActivity
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.join.JoinFormFragment
import com.example.easycarpoolapp.auth.join.JoinPhoneFragment
import com.example.easycarpoolapp.auth.join.JoinPhoneVerifyFragment
import com.example.easycarpoolapp.databinding.ActivityAuthBinding
import com.example.easycarpoolapp.intro.InfoActivity

class AuthActivity : AppCompatActivity(), AuthMainFragment.Callbacks, JoinPhoneFragment.Callbacks, JoinPhoneVerifyFragment.CallBacks, JoinFormFragment.Callbacks{

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
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }

    override fun onLoginSuccessed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onNextSelected(phoneNumber : String) {
        val fragment = JoinPhoneVerifyFragment.getInstance(phoneNumber)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }

    override fun afterVerified(phoneNumber: String) {
        val fragment = JoinFormFragment.getInstance(phoneNumber)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }

    override fun onJoinTransactionSuccess() {
        val fragment = AuthMainFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .replace(binding.fragmentContainer.id, fragment).addToBackStack(null).commit()
    }   // 회원가입 성공시 로그인 창으로 이동


}