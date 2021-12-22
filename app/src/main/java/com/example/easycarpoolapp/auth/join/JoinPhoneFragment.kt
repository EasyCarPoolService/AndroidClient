package com.example.easycarpoolapp.auth.join

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentJoinPhoneBinding


class JoinPhoneFragment() : Fragment() {

    interface Callbacks{
        public fun onNextSelected(phoneNumber : String)
    }


    companion object{
        public fun getInstance() : JoinPhoneFragment{
            return JoinPhoneFragment()
        }
    }

    private var callbacks : Callbacks? = null
    private lateinit var binding : FragmentJoinPhoneBinding
    private val viewModel : JoinPhoneViewModel by lazy {
        ViewModelProvider(this).get(JoinPhoneViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setUI()

        binding.btnNext.setOnClickListener {
            var phoneNumber = binding.editPhone.text.toString()
            viewModel.sendCode()
            callbacks!!.onNextSelected(phoneNumber)
        }
    }

    private fun setUI(){
        binding.editPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                viewModel.phoneNumber.value = s?.toString()

                if(s!!.length==0){
                    binding.btnNext.setEnabled(false)
                    binding.btnNext.setClickable(false)
                    binding.btnNext.setBackgroundResource(R.drawable.btn_radius_gray)
                }else{
                    binding.btnNext.setEnabled(true)
                    binding.btnNext.setClickable(true)
                    binding.btnNext.setBackgroundResource(R.drawable.btn_radius_main)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}