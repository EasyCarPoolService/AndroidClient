package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.databinding.FragmentRequestPassengerDialogBinding
import com.example.easycarpoolapp.fragment.post.PostHomeFragment
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import javax.security.auth.callback.Callback


class RequestPassengerDialogFragment(val postDto : PostDto, val hostFragment : Fragment, val buttonLayout : String) : DialogFragment(){


    interface  Callbacks{
        //요청하기 버튼 클릭시 이벤트
        public fun onPassengerRequestButtonClicked()
    }

    private lateinit var binding : FragmentRequestPassengerDialogBinding
    private var callbacks : Callbacks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbacks = hostFragment as Callbacks?
    }


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSendRequestChat.setOnClickListener {
            callbacks?.onPassengerRequestButtonClicked()
            dismiss()
        }   //ChatFragment -> 상대방에게 요청 전송

        binding.btnCancelChat.setOnClickListener {
            dismiss()
        }   //ChatFragment -> Dialog종료

        binding.btnConfirmCalendar.setOnClickListener {
            dismiss()
        }   //CalendarHomeFragmnet -> Dialog종료



    }//onViewCreated()

    override fun onResume() {
        super.onResume()

        //dialogFragment의 레이아웃 넓이와 높이는 onResume에서 수행
        setDialogSize()


    }// onResume()


    private fun setUI(){

        if(buttonLayout.equals("chat")){    //chatFragment에서 호스팅중
            binding.layoutBtnChat.visibility = View.VISIBLE
        }else{  //CalendarHomeFragment에서 호스팅중
            binding.btnConfirmCalendar.visibility = View.VISIBLE
        }


        binding.textNickname.text = postDto.nickname
        binding.textGender.text = postDto.gender
        binding.textDeparture.text = postDto.departure
        binding.textDestination.text = postDto.destination
        binding.textDate.text = "날짜 : "+postDto.departureDate+"  시간 : "+postDto.departureTime
        binding.textMessage.text = postDto.message
        binding.ratingBar.rating = postDto.rate
        binding.textRate.text = postDto.rate.toString()

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