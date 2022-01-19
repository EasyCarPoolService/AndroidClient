package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostDetailBinding
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto


class PostDetailFragment : Fragment() {

    companion object{
        public fun getInstance(item: PostPassengerDto): PostDetailFragment{
            val bundle = Bundle().apply {

                putSerializable("nickname", item.nickname)
                putSerializable("gender", item.gender)
                //putSerializable("rate", item.rate)  추후 기능 추가
                putSerializable("departure", item.departure)
                putSerializable("destination", item.destination)
                putSerializable("date", item.departureDate)
                putSerializable("time", item.departureTime)
                putSerializable("message", item.message)
            }
            return PostDetailFragment().apply { arguments = bundle }
        }
    }//companion object

    lateinit var binding : FragmentPostDetailBinding
    var nickname : String? = null
    var gender : String? = null
    var departure : String? =null
    var destination : String? = null
    var date : String? = null
    var time : String? = null
    var message : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nickname = arguments?.getString("nickname")
        gender = arguments?.getString("gender")
        departure = arguments?.getString("departure")
        destination = arguments?.getString("destination")
        date = arguments?.getString("date")
        time = arguments?.getString("time")
        message = arguments?.getString("message")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail, container, false)
        setUI()

        binding.btnSendMessage.setOnClickListener {
            Toast.makeText(requireContext(), "send message to ..", Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }//onCreateView

    private fun setUI(){

        binding.textNickname.text = nickname
        binding.textGender.text = gender
        binding.textDeparture.text = departure
        binding.textDestination.text = destination
        binding.textDate.text = "날짜 : "+date+"  시간 : "+time
        binding.textMessage.text = message

    }//setUI





}