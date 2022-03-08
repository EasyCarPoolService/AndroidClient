package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostDetailBinding
import com.example.easycarpoolapp.databinding.FragmentPostDriverDetailBinding
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto


class PostDriverDetailFragment : Fragment() {



    interface Callbacks{
        //방생성후 방 UUID를 서버로 부터 전달받아 Observer패턴에 의해 Fragment이동

        //check Passenger 와 구분하도록 수정
        public fun onSendMesageSelected(dto : ChatRoomDto)
    }


    companion object{
        public fun getInstance(item: PostDto): PostDriverDetailFragment{
            val bundle = Bundle().apply {

                putSerializable("postType", item.type)
                putLong("postId", item.postId)
                putSerializable("email", item.email)    //UI에 띄우지 않지만 Message전송을 위해 데이터 저장
                putSerializable("nickname", item.nickname)
                putSerializable("gender", item.gender)
                putFloat("rate", item.rate)
                putSerializable("departure", item.departure)
                putSerializable("destination", item.destination)
                putSerializable("date", item.departureDate)
                putSerializable("time", item.departureTime)
                putSerializable("message", item.message)
                putSerializable("fcmToken", item.fcmToken)
            }
            return PostDriverDetailFragment().apply { arguments = bundle }
        }
    }//companion object

    private val TAG : String = "PostDriverDetail"
    private lateinit var binding : FragmentPostDriverDetailBinding
    private var callbacks : PostDriverDetailFragment.Callbacks? = null
    private val viewModel : PostDriverDetailViewModel by lazy {
        ViewModelProvider(this).get(PostDriverDetailViewModel::class.java)
    }

    var postType : String? = null
    var postId : Long? = null
    var email : String? = null
    var nickname : String? = null
    var gender : String? = null
    var rate : Float? = null
    var departure : String? =null
    var destination : String? = null
    var date : String? = null
    var time : String? = null
    var message : String? = null
    var fcmToken : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbacks = context as Callbacks?

        postType = arguments?.getString("postType")
        postId = arguments?.getLong("postId")
        email = arguments?.getString("email")
        nickname = arguments?.getString("nickname")
        gender = arguments?.getString("gender")
        rate = arguments?.getFloat("rate")
        departure = arguments?.getString("departure")
        destination = arguments?.getString("destination")
        date = arguments?.getString("date")
        time = arguments?.getString("time")
        message = arguments?.getString("message")
        fcmToken = arguments?.getString("fcmToken")

        Log.e(TAG, postId.toString())

    }//onCreate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_driver_detail, container, false)
        setUI()


        binding.btnSendMessage.setOnClickListener {
            //게시글 작성자에게 메시지 보내기 -> 대화창 생성및 이동
            viewModel.createRoom(
                postType = postType!!,
                postId = postId!!,
                driver = LocalUserData.getEmail()!!,
                passenger = email!!,
                driverNickname = LocalUserData.getNickname()!!,
                passengerNickname = nickname!!,
                driverFcmToken = LocalUserData.getFcmToken()!!,
                passengerFcmToken = fcmToken!!
            )
        }

        return binding.root
    }//onCreateView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()

        viewModel.roomInfo.observe(viewLifecycleOwner, Observer {
            callbacks!!.onSendMesageSelected(dto = it)
        })

    }//onViewCreated

    private fun setUI(){

        binding.textNickname.text = nickname
        binding.textGender.text = gender
        binding.textDeparture.text = departure
        binding.textDestination.text = destination
        binding.textDate.text = "날짜 : "+date+"  시간 : "+time
        binding.textMessage.text = message
        binding.ratingBar.rating = rate!!
        binding.textRate.text = rate!!.toString()

    }//setUI

    private fun setImage(){

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+email)
            .into(binding.imageViewProfile) //profile image set

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/car?email="+email)
            .into(binding.imageCar) //profile image set

    }//setImage()



}