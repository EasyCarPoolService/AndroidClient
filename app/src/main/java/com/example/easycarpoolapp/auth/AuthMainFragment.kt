package com.example.easycarpoolapp.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentAuthMainBinding
import java.lang.Exception


class AuthMainFragment() : Fragment() {

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
    private val viewModel : AuthMainViewModel by lazy {
        ViewModelProvider(this).get(AuthMainViewModel::class.java)
    }



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

        setEmailTextWathcer()
        setPasswordTextWathcer()

        binding.btnLogin.setOnClickListener {
            try {
                viewModel.login()
            } catch (e : Exception) {
                Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnJoin.setOnClickListener {
            callbacks!!.onJoinSelected()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun setEmailTextWathcer(){
        binding.editEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.email = s.toString()
            }

        })
    }
    private fun setPasswordTextWathcer(){
        binding.editPassword.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.password = s.toString()
            }

        })
    }




}