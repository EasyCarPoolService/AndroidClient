package com.example.easycarpoolapp.fragment.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.auth.dto.LocalUserDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto

class ChatHomeViewModel : ViewModel(){

    val repository : ChatRepository? = ChatRepository.getInstance()
    val roomList : MutableLiveData<ArrayList<ChatRoomDto>> = MutableLiveData()
    val leaveRoomFlag : MutableLiveData<Boolean> = MutableLiveData()

    //현재 사용자가 참여한 모든 채팅방 불러오기
    public fun getChatRoom(){
        repository?.getChatRoom(LocalUserDto(email = LocalUserData.getEmail()), roomList)
    }


    fun leaveChatRoom(chatRoomDto: ChatRoomDto) {
        repository?.leaveChatRoom(chatRoomDto, leaveRoomFlag)
    }   //채팅방 나가기   -> 채팅방 나간후 RecyclerView에 채팅방 목록 업데이트할것인지 체크하기

}