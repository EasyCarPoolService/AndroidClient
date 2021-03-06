package com.example.easycarpoolapp.fragment.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentChatHomeBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto

class ChatHomeFragment : Fragment(), LeaveRoomDialogFragment.Callbacks{

    interface Callbacks{
        fun onChatRoomSelected(dto : ChatRoomDto)
    }

    companion object{
        public fun getInstance() : ChatHomeFragment = ChatHomeFragment()
    }

    private var currentFragment : ChatHomeFragment? = null
    private lateinit var binding : FragmentChatHomeBinding
    private var callbacks : Callbacks? = null
    private val viewModel :ChatHomeViewModel by lazy {
        ViewModelProvider(this).get(ChatHomeViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentFragment = this
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

        //???????????? ????????? ?????? ????????? ?????? ????????????
        viewModel.getChatRoom()

        return binding.root
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.roomList.observe(viewLifecycleOwner, Observer{
            updateUI(it)
        })

        viewModel.leaveRoomFlag.observe(viewLifecycleOwner, Observer {
            if(it!!){   //true transaction ???????????? -> recyclerview ??? ??????
                viewModel.getChatRoom() // chatroom ?????? ????????????
            }
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
        val time : TextView = itemView.findViewById(R.id.text_time)
        val imageView : ImageView = itemView.findViewById(R.id.item_chat_home_profile)
        var opponentEmail : String? = null
        init{
            itemView.setOnClickListener {
                callbacks?.onChatRoomSelected(dto = dto)    //????????? ?????? -> ?????? ????????? Fragment ?????? -> ?????? ???????????? ????????????
            }
            itemView.setOnLongClickListener {
                LeaveRoomDialogFragment(hostFragment = currentFragment!!, chatRoomDto = dto).show(requireActivity().supportFragmentManager, "LeaveRoomDialogRragment")
                true
            }   //Long Click??? -> ????????? ????????? DialogFragment????????? ->  DialogFragment?????? ?????? ?????? -> Callbacks ??????


        }   //init()

        fun bind(item : ChatRoomDto){
            dto = item

            if(LocalUserData.getNickname() == item.driverNickname){
                nickname.text = item.passengerNickname
                opponentEmail = item.passenger
            }else{
                nickname.text = item.driverNickname
                opponentEmail = item.driver
            }

            if(item.lastMessage.equals("request reservation")){
                content.text = "?????? ??????!"
            }else if(item.lastMessage.equals("confirm of request")){
                content.text = "?????? ??????!"
            } else{
                content.text = item.lastMessage
            }

            time.text = item.lastChatTime

            //???????????? ????????? ?????????
            Glide.with(requireContext())
                .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+opponentEmail)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)  // ???????????? ?????? ?????? -> ????????? ???????????? ?????? x
                .into(imageView)
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


    //check ????????? ????????? ????????? ???????????? ?????? RecyclerView update?????? ????????????
    override fun onConfirmSelectedFromLeaveRoomDialog(chatRoomDto : ChatRoomDto) {
        viewModel.leaveChatRoom(chatRoomDto)
    }   //LeaveRoomDialogFragment() -> ?????? ?????? ?????? -> Callback??????




}