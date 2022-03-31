package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentLeaveRoomDialogBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto

class LeaveRoomDialogFragment(val hostFragment : Fragment, val chatRoomDto : ChatRoomDto) : DialogFragment() {

    interface Callbacks {
        fun onConfirmSelectedFromLeaveRoomDialog(chatRoomDto : ChatRoomDto)
    }

    private var callbacks : Callbacks? = null
    private lateinit var binding : FragmentLeaveRoomDialogBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = hostFragment as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_room_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        val param : ViewGroup.LayoutParams = dialog!!.window!!.attributes
        param.width = WindowManager.LayoutParams.MATCH_PARENT
        param.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = param as WindowManager.LayoutParams

        return binding.root
    }  //onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            callbacks?.onConfirmSelectedFromLeaveRoomDialog(chatRoomDto = chatRoomDto)
            dismiss()
        }   //확인 클릭시 채팅방 나가기 수행

    }   //onViewCreated()

    override fun onResume() {
        super.onResume()

    } //onResume()

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    } // onDetach()



}