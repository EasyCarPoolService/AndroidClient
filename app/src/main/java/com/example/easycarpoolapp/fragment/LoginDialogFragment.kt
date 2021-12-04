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

    private lateinit var binding : FragmentLoginDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)


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
            Toast.makeText(requireContext(), "confirm", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }


        return binding.root
    }



    companion object {



    }
}