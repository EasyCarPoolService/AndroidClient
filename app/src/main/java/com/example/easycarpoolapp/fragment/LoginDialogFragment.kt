package com.example.easycarpoolapp.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentLoginDialogBinding

class LoginDialogFragment: DialogFragment(){

    interface Callbacks{
        fun onConfirmSelectedFromLoginDialog() //login activity로 이동
    }

    private var callbacks : Callbacks? = null

    private lateinit var binding : com.example.easycarpoolapp.databinding.FragmentLoginDialogBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val param : ViewGroup.LayoutParams = dialog!!.window!!.attributes
        param.width = WindowManager.LayoutParams.MATCH_PARENT
        param.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = param as WindowManager.LayoutParams


        binding.btnCancel.setOnClickListener {
            Toast.makeText(requireContext(), "cancel", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            callbacks!!.onConfirmSelectedFromLoginDialog()
            this.dismiss()
        }   // 확인 클릭시 로그인 Fragment로 이동


        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}