package com.example.easycarpoolapp.fragment.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentChatBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto


// ChatHomeFragment에서 repository를 생성해야함 유념


class ChatFragment : Fragment() {

    companion object{
        public fun getInstance(dto : ChatRoomDto) : ChatFragment{
            val bundle = Bundle().apply {
                putSerializable("roomid", dto.roomId)
                putSerializable("driver", dto.driver)
                putSerializable("passenger", dto.passenger)
                putSerializable("drivernickname", dto.driverNickname)
                putSerializable("passengernickname", dto.passengerNickname)
            }

            return ChatFragment().apply { arguments = bundle }
        }
    }

    // 각 사용자의 닉네임 사용 여부 판단
    private lateinit var binding : FragmentChatBinding
    private lateinit var roomId : String
    private lateinit var driver : String
    private lateinit var passenger : String
    private lateinit var driverNickname : String
    private lateinit var passengerNickname : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ChatRepository.init(requireContext())

        roomId = arguments?.getString("roomid")!!
        driver = arguments?.getString("driver")!!
        passenger = arguments?.getString("passenger")!!
        driverNickname = arguments?.getString("drivernickname")!!
        passengerNickname = arguments?.getString("passengernickname")!!
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)


        return binding.root
    }

}