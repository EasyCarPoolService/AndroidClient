package com.example.easycarpoolapp.auth

import android.content.Context
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

    interface Callbacks{
        fun onJoinSelected() //login activity로 이동
    }

    private var callbacks : Callbacks? = null



    private lateinit var binding : FragmentAuthMainBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnJoin.setOnClickListener {
            callbacks!!.onJoinSelected()
        }





    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


}