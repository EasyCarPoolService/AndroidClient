package com.example.easycarpoolapp.auth.join

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentJoinPhoneBinding


class JoinPhoneFragment private constructor() : Fragment() {

    companion object{
        public fun getInstance() : JoinPhoneFragment{
            return JoinPhoneFragment()
        }
    }

    private lateinit var binding : FragmentJoinPhoneBinding
    private val viewModel : JoinViewModel by lazy {
        ViewModelProvider(this).get(JoinViewModel::class.java)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_phone, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAuthPhone()

    }

    private fun setAuthPhone(){
        binding.editPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(s!!.length==0){
                    binding.btnNext.setBackgroundResource(R.drawable.btn_radius_gray)
                }else{
                    binding.btnNext.setBackgroundResource(R.drawable.btn_radius_main)
                }
            }
            override fun afterTextChanged(s: Editable?) {}

        })

    }



}