package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.databinding.FragmentRequestPassengerDialogBinding
import com.example.easycarpoolapp.fragment.post.dto.PostDto


class RequestPassengerDialogFragment(val postDto : PostDto) : DialogFragment(){


    private lateinit var binding : FragmentRequestPassengerDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestPassengerDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        setUI()

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+postDto.email)
            .into(binding.imageViewProfile)

        return binding.root
    }//onCreateView()

    override fun onResume() {
        super.onResume()

        //dialogFragment의 레이아웃 넓이와 높이는 onResume에서 수행
        setDialogSize()


    }// onResume()


    private fun setUI(){

        binding.textNickname.text = postDto.nickname
        binding.textGender.text = postDto.gender
        binding.textDeparture.text = postDto.departure
        binding.textDestination.text = postDto.destination
        binding.textDate.text = "날짜 : "+postDto.departureDate+"  시간 : "+postDto.departureTime
        binding.textMessage.text = postDto.message

    }//setUI

    private fun setDialogSize(){
        val windowManager = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        size.x // 디바이스 가로 길이
        size.y // 디바이스 세로 길이

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.8).toInt()
        params?.height = (deviceHeight * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }//setDialogSize()

}