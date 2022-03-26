package com.example.easycarpoolapp.fragment.chat

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.example.easycarpoolapp.databinding.FragmentChatBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatDto
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.sothree.slidinguppanel.SlidingUpPanelLayout


// ChatHomeFragment에서 repository를 생성해야함 유념

class ChatFragment : Fragment() , RequestPassengerDialogFragment.Callbacks, RequestDriverDialogFragment.Callbacks{

    companion object{
        public fun getInstance(dto : ChatRoomDto) : ChatFragment{
            val bundle = Bundle().apply {
                putSerializable("roomid", dto.roomId)
                putSerializable("postType", dto.postType)
                putLong("postId", dto.postId!!)
                putSerializable("driver", dto.driver)
                putSerializable("passenger", dto.passenger)
                putSerializable("drivernickname", dto.driverNickname)
                putSerializable("passengernickname", dto.passengerNickname)
                putSerializable("driverFcmToken", dto.driverFcmToken)
                putSerializable("passengerFcmToken", dto.passengerFcmToken)

            }

            return ChatFragment().apply { arguments = bundle }
        }
    }//companion object

    // 각 사용자의 닉네임 사용 여부 판단
    private lateinit var binding : FragmentChatBinding
    private lateinit var roomId : String
    private lateinit var postType : String
    private var postId : Long? = null
    private lateinit var driver : String
    private lateinit var passenger : String
    private lateinit var driverNickname : String
    private lateinit var passengerNickname : String
    private lateinit var driverFcmToken : String
    private lateinit var passengerFcmToken : String

    private lateinit var opponentFcmToken : String

    private lateinit var adapter : ChatAdapter
    private val viewModel : ChatViewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChatRepository.init(requireContext())

        roomId = arguments?.getString("roomid")!!
        postType = arguments?.getString("postType")!!
        postId = arguments?.getLong("postId")!!
        driver = arguments?.getString("driver")!!
        passenger = arguments?.getString("passenger")!!
        driverNickname = arguments?.getString("drivernickname")!!
        passengerNickname = arguments?.getString("passengernickname")!!
        driverFcmToken = arguments?.getString("driverFcmToken")!!
        passengerFcmToken = arguments?.getString("passengerFcmToken")!!


        viewModel.getPostInfo(postType = postType, postId = postId!!)
    }// onCreate()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        //구독요청
        viewModel.subscribe(roomId)


        return binding.root
    } //onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setRecycler()
        opponentFcmToken = findOpponentFcmToken()
        binding.btnSend.setOnClickListener {
            val message : String = binding.editMessage.text.toString()
            binding.editMessage.setText("")
            viewModel.sendMessage(message, opponentFcmToken, "message")
        }



        binding.btnPlus.setOnClickListener {
            val state = binding.slideLayout.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.slideLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
        }

        binding.btnRequest.setOnClickListener {

            binding.slideLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

            if(postType.equals("passenger")){
                viewModel.postInfo.value?.let { it1 -> RequestPassengerDialogFragment(it1, this, buttonLayout = "chat").show(requireActivity().supportFragmentManager, "RequestPasengerDialog") }
            }else{
                viewModel.postInfo.value?.let { it1 -> RequestDriverDialogFragment(it1, this, buttonLayout = "chat").show(requireActivity().supportFragmentManager, "RequestDriverDialog") }
            }


        }   // 상대방에게 요청 보내기

    }//onViewCreated

    private fun findOpponentFcmToken() : String{

        var temp : String

        if (driverFcmToken == LocalUserData.getFcmToken()){
            temp = passengerFcmToken
        }else{
            temp = driverFcmToken
        }
        return temp
    }


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


    override fun onPassengerRequestButtonClicked() {
        viewModel.sendMessage("request reservation", opponentFcmToken, "request")
    }// RequestPassengerDialogFragment.Callbacks 구현



    override fun onDriverRequestButtonClicked() {
        viewModel.sendMessage("request reservation", opponentFcmToken, "request")
    }// RequestPassengerDialogFragment.Callbacks 구현

    


    //==========================================================================================================

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val myLayout = itemView.findViewById<LinearLayout>(R.id.chat_layout_mine)
        val my_request_layout = itemView.findViewById<LinearLayout>(R.id.request_mine_layout)
        val opponent_request_layout = itemView.findViewById<LinearLayout>(R.id.request_opponent_layout)
        val opponent_layout = itemView.findViewById<LinearLayout>(R.id.chat_layout_opponent)
        val opponent_message = itemView.findViewById<TextView>(R.id.chat_text_content_opponent)
        val opponent_time = itemView.findViewById<TextView>(R.id.chat_text_time_opponent)
        val my_message = itemView.findViewById<TextView>(R.id.chat_text_content_mine)
        val my_time = itemView.findViewById<TextView>(R.id.chat_text_time_mine)
        val opponent_profile = itemView.findViewById<ImageView>(R.id.chat_profile_opponent)


        //request 상대방
        val opponent_driver = itemView.findViewById<TextView>(R.id.request_driver_opponent)
        val opponent_passenger = itemView.findViewById<TextView>(R.id.request_passenger_opponent)
        val opponent_departure = itemView.findViewById<TextView>(R.id.request_departure_opponent)
        val opponent_destination = itemView.findViewById<TextView>(R.id.request_destination_opponent)
        val opponent_departure_time = itemView.findViewById<TextView>(R.id.request_time_opponent)
        val btn_confirm_opponent = itemView.findViewById<Button>(R.id.btn_request_confirm_opponent)


        //request 현재 사용자
        val mine_driver = itemView.findViewById<TextView>(R.id.request_driver_mine)
        val mine_passenger = itemView.findViewById<TextView>(R.id.request_passenger_mine)
        val mine_departure = itemView.findViewById<TextView>(R.id.request_departure_mine)
        val mine_destination = itemView.findViewById<TextView>(R.id.request_destination_mine)
        val mine_departure_time = itemView.findViewById<TextView>(R.id.request_time_mine)

        //confirm layout
        val confirm_opponent = itemView.findViewById<LinearLayout>(R.id.confirm_opponent_layout)
        val confirm_mine = itemView.findViewById<LinearLayout>(R.id.confirm_mine_layout)


        public fun bind(item : ChatDto){
            if(item.writer == LocalUserData.getEmail()){ //내가 작성한 글
                myLayout.visibility = View.VISIBLE
                if(item.type.equals("message")){
                    my_message.apply {
                        visibility = View.VISIBLE
                        text = item.message
                    }
                }else if(item.type.equals("request")){
                    my_request_layout.visibility = View.VISIBLE
                    mine_driver.text = mine_driver.text.toString()+driverNickname
                    mine_passenger.text = mine_passenger.text.toString()+passengerNickname
                    mine_departure.text = mine_departure.text.toString()+viewModel.postInfo.value?.departure
                    mine_destination.text = mine_destination.text.toString()+viewModel.postInfo.value?.destination
                    mine_departure_time.text = mine_departure_time.text.toString()+viewModel.postInfo.value?.departureTime

                }else{
                    //요청 확인 레이아웃 구성
                    confirm_mine.visibility = View.VISIBLE
                }

                my_time.text = item.time
                //time set 필요
            }else{  //상대가 작성한 글
                opponent_layout.visibility = View.VISIBLE
                if(item.type.equals("message")){
                    opponent_message.apply {
                        visibility = View.VISIBLE
                        text = item.message
                    }
                }else if(item.type.equals("request")){
                    opponent_request_layout.visibility = View.VISIBLE
                    opponent_driver.text = opponent_driver.text.toString()+driverNickname
                    opponent_passenger.text = opponent_passenger.text.toString()+passengerNickname
                    opponent_departure.text = opponent_departure.text.toString()+viewModel.postInfo.value?.departure
                    opponent_destination.text = opponent_destination.text.toString()+viewModel.postInfo.value?.destination
                    opponent_departure_time.text = opponent_departure_time.text.toString()+viewModel.postInfo.value?.departureTime
                }else{
                    //요청 수락에 대한 레이아웃 구성
                    confirm_opponent.visibility = View.VISIBLE
                }

                opponent_time.text = item.time

                Glide.with(this@ChatFragment)
                    .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+item.writer)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)  // 선언하지 않을 경우 -> 서버의 변경사항 반영 x
                    .into(opponent_profile)
            }
        }//bind

        init{
            btn_confirm_opponent.setOnClickListener {

                viewModel.registerReservedPost(postId = postId!!, postType = postType, driver = driver, driverFcmToken = driverFcmToken, passenger = passenger, passengerFcmToken = passengerFcmToken, date = viewModel.postInfo.value!!.departureDate, time = viewModel.postInfo.value!!.departureTime)
                viewModel.sendMessage("confirm of request", opponentFcmToken, "confirm")
            }
        } //init()
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