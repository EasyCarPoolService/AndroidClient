package com.example.easycarpoolapp.navigation.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentProfileHomeBinding
import com.example.easycarpoolapp.fragment.post.PostRepository
import com.example.easycarpoolapp.navigation.NavigationRepository

class ProfileHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : ProfileHomeFragment = ProfileHomeFragment()
    }

    interface Callbacks{
        public fun onEditProfileSelected()
        public fun onReportUserSelected()
    }


    private lateinit var binding : FragmentProfileHomeBinding
    private var callbacks : Callbacks? = null
    private val viewModel : ProfileHomeViewModel by lazy {
        ViewModelProvider(this).get(ProfileHomeViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }//onAttach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationRepository.init(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_home, container, false)
        setUIWithLocalData()
        setUIWithServerData()

        binding.btnEditProfile.setOnClickListener {
            callbacks?.onEditProfileSelected()  //HostingActivity = MainActivity
        }   //프로필 편집 버튼 클릭 -> 편집창 프래그먼트로 이동

        binding.layoutReportUser.setOnClickListener {
            callbacks?.onReportUserSelected()
        }



        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userPostDto.observe(viewLifecycleOwner, Observer {
            binding.userPostDriver.text = it?.driver
            binding.userPostPassenger.text = it?.passenger
            binding.userPostOngoing.text = it?.ongoing
        })


    }//onViewCreated

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }//onDetach


    private fun setUIWithLocalData(){
        binding.textProfileNickName.text = LocalUserData.getNickname()
        binding.ratingBar.rating = LocalUserData.getRate()!!
        //binding.textRate.text = LocalUserData.getRate().toString()
        binding.textRate.text = String.format("%.1f", LocalUserData.getRate())

    }// 기기에 저장되어 있는 정보 로드하여 UI에 띄우기

    private fun setUIWithServerData(){
        viewModel.getUserPostData()
        setImageBtnProfile() //프로필 이미지 설정
    }   //서버에 저장되어 있는 정보 조회하여 UI에 띄우기

    private fun setImageBtnProfile() {
        Glide.with(this)
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+LocalUserData.getEmail())
            .into(binding.btnProfile)

    }//프로필 이미지 설정

}