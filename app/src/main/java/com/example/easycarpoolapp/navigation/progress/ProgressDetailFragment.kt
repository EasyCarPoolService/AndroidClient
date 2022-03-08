package com.example.easycarpoolapp.navigation.progress

import android.media.Rating
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentProgressDetailBinding
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.navigation.NavigationRepository

class ProgressDetailFragment : Fragment() {

    companion object{
        public fun getInstance(item : PostDto) : ProgressDetailFragment{
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
            }
            return ProgressDetailFragment().apply { arguments = bundle}
        }
    }


    private lateinit var binding : FragmentProgressDetailBinding
    private val viewModel : ProgressDetailViewModel by lazy {
        ViewModelProvider(this).get(ProgressDetailViewModel::class.java)
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationRepository.init(requireContext())


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


    }//onCreate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress_detail, container, false)
        setRatingBar()  //ratingbar listener 추가

        binding.btnProgressComplete.setOnClickListener {
            viewModel.progressToComplete(postId = postId,
                host_email = email,
                host_nickname = nickname,
                rate = binding.ratingBarRate.rating,
                editRateTitle = binding.editRateTitle.text.toString(),
                editRateContent = binding.editRateContent.text.toString())
        }

        return binding.root
    }   //onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        setImage()
        viewModel.completeFlag.observe(viewLifecycleOwner, Observer {
            if(it) terminateFragment()
        })
    } // onViewCreated

    private fun setRatingBar(){
        binding.ratingBarRate.setOnRatingBarChangeListener { ratingBar, fl, b ->
            /*ratingbar_indicator.setRating(rating);
            ratingbar_small.setRating(rating);*/
            ratingBar.apply {
                rating = fl
            }
            binding.textRateRating.text = fl.toString() //Rating TextView 상태 변경
        }


    }//setRatingBar()

    private fun setUI(){
        binding.textNickname.text = nickname
        binding.textGender.text = gender
        binding.textDeparture.text = departure
        binding.textDestination.text = destination
        binding.textDate.text = "날짜 : "+date+"  시간 : "+time
        binding.textMessage.text = message
        binding.ratingBarState.rating = rate!!
        binding.textRate.text = rate!!.toString()
    }//setUI()

    private fun setImage(){

        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+email)
            .into(binding.imageViewProfile) //profile image set

    }//setImage()

    private fun terminateFragment(){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }



}