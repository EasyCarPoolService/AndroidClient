package com.example.easycarpoolapp.auth.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentJoinFormBinding


class JoinFormFragment private constructor(): Fragment() {

    companion object{
        public fun getInstance(phoneNumber : String) : JoinFormFragment{
            val args = Bundle().apply{
                putSerializable("phoneNumber", phoneNumber)
            }
            return JoinFormFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var binding : FragmentJoinFormBinding
    private val viewModel : JoinFormViewModel by lazy {
        ViewModelProvider(this).get(JoinFormViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.phoneNumber = arguments?.getString("phoneNumber")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_form, container, false)

        return binding.root
    }

}