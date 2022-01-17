package com.example.easycarpoolapp.navigation.car

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentRegisterCarBinding

class RegisterCarFragment : Fragment() {

    interface CallBacks{
        public fun onNextSelectedFromRegisterCar()
    }

    companion object{
        public fun getInstance() : RegisterCarFragment{
            return RegisterCarFragment()
        }
    }

    private var callbacks : RegisterCarFragment.CallBacks? = null
    private lateinit var binding : FragmentRegisterCarBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callbacks = context as CallBacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_car, container, false)

        binding.btnNext.setOnClickListener {
            callbacks?.onNextSelectedFromRegisterCar()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null

        //call repository onDestroy
    }
}