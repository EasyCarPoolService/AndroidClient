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
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentHomeBinding
import com.example.easycarpoolapp.fragment.LoginDialogFragment


class HomeFragment private constructor(): Fragment() {


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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTest.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p3 ==0){
                    binding.btnTest.setBackgroundResource(R.drawable.btn_radius_gray )
                }else{
                    binding.btnTest.setBackgroundResource(R.drawable.btn_radius_main )
                    //binding.btnTest.setBackgroundColor(Color.BLUE)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })




        var count=0
        binding.btnTest.setOnClickListener {
            Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
            count++
            if(count%2==1){
                binding.btnTest.setBackgroundColor(resources.getColor(R.color.main_color))
            }else{
                binding.btnTest.setBackgroundColor(Color.BLUE)
            }




        }
    }



}