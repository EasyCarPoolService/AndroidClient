package com.example.easycarpoolapp.fragment.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentChatBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import org.json.JSONObject


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
    private lateinit var adapter : ChatAdapter
    private val viewModel : ChatViewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }


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

        //구독요청
        viewModel.subscribe(roomId)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()

        binding.btnSend.setOnClickListener {
            val message : String = binding.editMessage.text.toString()
            viewModel.sendMessage(message)
        }

    }//onViewCreated

    private fun setRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChatAdapter(ArrayList<ChatDto>())
        binding.recyclerView.adapter = adapter

        viewModel.findMessageByRoomId(roomId)

        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }//setRecycler

    private fun updateUI(items : ArrayList<ChatDto>){
        adapter = ChatAdapter(items)
        binding.recyclerView.adapter = adapter
    }//upadteUI


    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val myLayout = itemView.findViewById<LinearLayout>(R.id.chat_layout_mine)
        val opponent_layout = itemView.findViewById<LinearLayout>(R.id.chat_layout_opponent)
        val opponent_message = itemView.findViewById<TextView>(R.id.chat_text_content_opponent)
        val opponent_time = itemView.findViewById<TextView>(R.id.chat_text_time_opponent)
        val my_message = itemView.findViewById<TextView>(R.id.chat_text_content_mine)
        val my_time = itemView.findViewById<TextView>(R.id.chat_text_time_mine)
        val opponent_profile = itemView.findViewById<ImageView>(R.id.chat_profile_opponent)


        public fun bind(item : ChatDto){
            if(item.writer == LocalUserData.getEmail()){ //내가 작성한 글
                myLayout.visibility = View.VISIBLE
                opponent_layout.visibility = View.INVISIBLE
                my_message.text = item.message
                my_time.text = item.time
                //time set 필요
            }else{  //상대가 작성한 글
                myLayout.visibility = View.INVISIBLE
                opponent_layout.visibility = View.VISIBLE
                opponent_message.text = item.message
                opponent_time.text = item.time
                //check
                Glide.with(this@ChatFragment)
                    .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+item.writer)
                    .into(opponent_profile)
            }
        }
    }
    inner class ChatAdapter(val items : ArrayList<ChatDto>) : RecyclerView.Adapter<ChatHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            val view = layoutInflater.inflate(R.layout.item_chat_layout, parent, false)
            return ChatHolder(view)
        }

        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }




}