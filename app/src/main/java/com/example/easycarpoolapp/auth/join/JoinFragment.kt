package com.example.easycarpoolapp.auth.join

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentJoinBinding
import com.example.easycarpoolapp.databinding.JoinAthenticatePhoneBinding


class JoinFragment private constructor() : Fragment() {

    companion object{
        public fun getInstance() : JoinFragment{
            return JoinFragment()
        }
    }

    private lateinit var binding : FragmentJoinBinding
    private lateinit var binding_phone : JoinAthenticatePhoneBinding
    private val viewModel : JoinViewModel by lazy {
        ViewModelProvider(this).get(JoinViewModel::class.java)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join, container, false)
        binding_phone = DataBindingUtil.inflate(inflater, R.layout.join_athenticate_phone, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAuthPhone()


    }

    @SuppressLint("ResourceAsColor")
    private fun setAuthPhone(){
        //binding_phone.editPhone.addTextChangedListener(JoinTextWatcher(binding_phone.btnNext))
        binding_phone.editPhone.addTextChangedListener {
            var length = binding_phone.editPhone.text.toString().length

            Log.e("LENGTH", length.toString())


        }




    }





}