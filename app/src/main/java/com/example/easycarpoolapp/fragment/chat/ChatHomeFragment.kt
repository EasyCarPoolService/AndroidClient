package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentChatHomeBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto

class ChatHomeFragment : Fragment() {

    interface Callbacks{
        fun onChatRoomSelected(dto : ChatRoomDto)
    }

    companion object{
        public fun getInstance() : ChatHomeFragment = ChatHomeFragment()
    }

    private lateinit var binding : FragmentChatHomeBinding
    private var callbacks : Callbacks? = null
    private val viewModel :ChatHomeViewModel by lazy {
        ViewModelProvider(this).get(ChatHomeViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    } // onAttach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChatRepository.init(requireContext())

    }//onCreate


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_home,container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ChatAdapter(ArrayList<ChatRoomDto>())

        //사용자가 참여한 모든 채팅방 정보 불러오기
        viewModel.getChatRoom()

        return binding.root
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roomList.observe(viewLifecycleOwner, Observer{
            updateUI(it)
        })

    }// onViewCreated

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    } //onDetach


    private fun updateUI(items : ArrayList<ChatRoomDto>){
        binding.recyclerView.adapter = ChatAdapter(items)
    }//updateUI


    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var dto : ChatRoomDto
        val nickname : TextView = itemView.findViewById(R.id.text_nickname)
        val content : TextView = itemView.findViewById(R.id.text_content)

        init{
            itemView.setOnClickListener {
                callbacks?.onChatRoomSelected(dto = dto)
            }
        }

        fun bind(item : ChatRoomDto){
            dto = item

            if(LocalUserData.getNickname() == item.driverNickname){
                nickname.text = item.passengerNickname
            }else{
                nickname.text = item.driverNickname
            }
        }

    }// ChatHolder


    inner class ChatAdapter(val items : ArrayList<ChatRoomDto>) : RecyclerView.Adapter<ChatHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            val view = layoutInflater.inflate(R.layout.item_chat_home_layout, parent, false)
            return ChatHolder(view)
        }

        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }    //ChatAdapter

}