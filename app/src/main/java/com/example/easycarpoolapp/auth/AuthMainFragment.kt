package com.example.easycarpoolapp.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentAuthMainBinding


class AuthMainFragment private constructor() : Fragment() {

    companion object{
        public fun getInstance() : AuthMainFragment{
            return AuthMainFragment()
        }
    }

    private lateinit var binding : FragmentAuthMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_main, container, false)


        return binding.root
    }


}