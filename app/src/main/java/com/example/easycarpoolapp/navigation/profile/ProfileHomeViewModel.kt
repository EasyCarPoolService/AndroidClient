package com.example.easycarpoolapp.navigation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.PostRepository
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto
import com.example.easycarpoolapp.navigation.NavigationRepository

class ProfileHomeViewModel : ViewModel() {

    private val repository : NavigationRepository? = NavigationRepository.getInstance()
    public var userPostDto : MutableLiveData<UserPostDto> = MutableLiveData()  //user가 작성한 게시글 혹은 진행중인 게시글 저장

    public fun getUserPostData(){
        repository!!.getUserPostData(userPostDto)
    }
}