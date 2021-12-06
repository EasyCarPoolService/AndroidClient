package com.example.easycarpoolapp.auth.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentJoinFormBinding


class JoinFormFragment private constructor(): Fragment() {

    companion object{
        public fun getInstance() : JoinFormFragment{
            return JoinFormFragment()
        }
    }

    private lateinit var binding : FragmentJoinFormBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_form, container, false)

        return binding.root
    }

}