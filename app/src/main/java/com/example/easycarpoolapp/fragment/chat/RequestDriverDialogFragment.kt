package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.databinding.FragmentRequestDriverDialogBinding
import com.example.easycarpoolapp.fragment.post.dto.PostDto

class RequestDriverDialogFragment(val postDto : PostDto, val hostFragment : Fragment, val buttonLayout : String) : DialogFragment(){

    interface Callbacks{
        public fun onDriverRequestButtonClicked()   // ChatHomeFragment에서 완료 버튼 클릭
    }


    private lateinit var binding : FragmentRequestDriverDialogBinding
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
        binding = FragmentRequestDriverDialogBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        setUI()


        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()

        binding.btnSendRequestChat.setOnClickListener {
            callbacks?.onDriverRequestButtonClicked()
            dismiss()
        }   //ChatFragment에서 -> 요청 전송
        binding.btnCancelChat.setOnClickListener {
            dismiss()
        }   //chatFragment에서 취소클릭 -> Dialog 종료

        binding.btnConfirmCalendar.setOnClickListener {
            dismiss()
        }   //CalarHomeFragment에서 Dialog 종료



    }// onViewCreated()

    override fun onResume() {
        super.onResume()
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

    }//setUI

    private fun setImage(){

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+postDto.email)
            .into(binding.imageViewProfile) //profile image set

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/car?email="+postDto.email)
            .into(binding.imageCar) //profile image set

    }//setImage()


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