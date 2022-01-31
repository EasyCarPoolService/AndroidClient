package com.example.easycarpoolapp.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentHomeBinding
import com.example.easycarpoolapp.fragment.LoginDialogFragment


class HomeFragment(): Fragment() {


    companion object{
        fun getInstance() : HomeFragment{
            return HomeFragment()
        }
    }

    private lateinit var binding : FragmentHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.btnShowUserdata.setOnClickListener {
            binding.textEmail.text = LocalUserData.getEmail()
            binding.textNickname.text = LocalUserData.getNickname()
            binding.textGender.text = LocalUserData.getGender()
            binding.textToken.text = LocalUserData.getToken()
            binding.textDriverAuth.text = LocalUserData.getDriverAuthentication().toString()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



}