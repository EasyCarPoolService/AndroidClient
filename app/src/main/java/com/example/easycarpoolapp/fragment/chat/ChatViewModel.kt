package com.example.easycarpoolapp.fragment.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto

class ChatViewModel : ViewModel(){

    private val repository = ChatRepository.getInstance()
    public var chatList : MutableLiveData<ArrayList<ChatDto>> = MutableLiveData()
    public var postInfo : MutableLiveData<PostDto> = MutableLiveData()
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
    public fun sendMessage(message: String, opponentFcmToken: String, type : String){
        //check for fcm push message
        repository?.sendMessage(roomId, type, LocalUserData.getEmail(), message, opponentFcmToken)
    }

    // 최초 방에 입장했을때 이전 메시지를 서버로 부터 로드
    fun findMessageByRoomId(roomId: String) {
        repository?.findMessageByRoomId(roomId, chatList)
    }


    //채팅방에 해당하는 게시글 조회하여 저장
    fun getPostInfo(postType : String, postId : Long) {
        repository?.getPostInfo(ChatRoomDto(postType = postType, postId = postId), postInfo)
    }

    fun registerReservedPost(postId : Long, driver : String, passenger : String, date : String, time : String){
        repository?.registerReservedPost(ReservedPostDto(
            postId = postId,
            driver = driver,
            passenger = passenger,
            date = date,
            time = time
        ))
    }//registerReservedPost()


}