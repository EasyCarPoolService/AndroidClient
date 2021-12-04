package com.example.easycarpoolapp.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.ActivityAuthBinding
import com.example.easycarpoolapp.fragment.home.HomeFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        if(supportFragmentManager.findFragmentById(binding.fragmentContainer.id)==null){
            supportFragmentManager.beginTransaction().add(binding.fragmentContainer.id, AuthMainFragment.getInstance()).commit()
        }

    }
}