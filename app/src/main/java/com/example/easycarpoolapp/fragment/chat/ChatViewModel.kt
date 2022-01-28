package com.example.easycarpoolapp.fragment.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import org.json.JSONObject

class ChatViewModel : ViewModel(){

    private val repository = ChatRepository.getInstance()
    public var chatList : MutableLiveData<ArrayList<ChatDto>> = MutableLiveData()
    private lateinit var roomId : String

    init {
        chatList.value = ArrayList<ChatDto>()
    }

    //특정 채팅방에대한 구독 요청
    public fun subscribe(roomId : String){
        this.roomId = roomId
        repository?.subscribe(roomId = roomId, chatList = chatList)
    }

    //현재 구독한 방에 대해 메시지 전송
    public fun sendMessage(message: String){
        repository?.sendMessage(roomId, LocalUserData.getEmail(), message)
    }

    // 최초 방에 입장했을때 이전 메시지를 서버로 부터 로드
    fun findMessageByRoomId(roomId: String) {
        repository?.findMessageByRoomId(roomId, chatList)
    }



}