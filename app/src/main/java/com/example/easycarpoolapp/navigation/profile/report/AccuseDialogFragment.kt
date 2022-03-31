package com.example.easycarpoolapp.navigation.profile.report

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.easycarpoolapp.databinding.FragmentAccuseDialogBinding

class AccuseDialogFragment(val hostFragment: Fragment) : DialogFragment() {

    interface Callbacks{
        public fun onAccuseConfirmSelected()
    }


    private var callbacks : Callbacks? = null
    private lateinit var binding : FragmentAccuseDialogBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = hostFragment as Callbacks?
    }//onAttach()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccuseDialogBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val param : ViewGroup.LayoutParams = dialog!!.window!!.attributes
        param.width = WindowManager.LayoutParams.MATCH_PARENT
        param.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = param as WindowManager.LayoutParams


        binding.btnCancel.setOnClickListener {
            Toast.makeText(requireContext(), "취소.", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            callbacks!!.onAccuseConfirmSelected()
            this.dismiss()
        }   // 확인 클릭시 로그인 Fragment로 이동

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }//onDetach()

}