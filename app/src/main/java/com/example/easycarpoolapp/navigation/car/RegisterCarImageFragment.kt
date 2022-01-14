package com.example.easycarpoolapp.navigation.car

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentRegisterCarImageBinding


class RegisterCarImageFragment : Fragment() {

    companion object{
        public fun getInstance() : RegisterCarImageFragment{
            return RegisterCarImageFragment()
        }
    }

    private lateinit var binding : FragmentRegisterCarImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_car_image, container, false)




        return binding.root
    }



}