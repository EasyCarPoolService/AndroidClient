package com.example.easycarpoolapp.fragment.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.post.dto.PostDistrictDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto

class PostHomeViewModel : ViewModel(){

    private val repository : PostRepository? = PostRepository.getInstance()
    public var postItems : MutableLiveData<ArrayList<PostDto>> = MutableLiveData()
    public var userPostDto : MutableLiveData<UserPostDto> = MutableLiveData()  //user가 작성한 게시글 혹은 진행중인 게시글 저장


    // 태워주세요 게시글 조회하여 RecyclerView에 띄우기
    public fun getPassengerPost(){
        repository!!.getPassengerPost(postItems)
    }

    // 타세요 게시글 조회하여 RecyclerView에 띄우기
    public fun getDriverPost(){
        repository!!.getDriverPost(postItems)
    }

    //User가 작성한 게시글 조회하여 RecyclerView에 띄우기
    fun getUserPost() {
        repository!!.getUserPost(postItems)
    }

    //Driver 인증 여부 결과 받아 LocalUserData 업데이트 수행
    public fun getDriverAuth(){
        repository!!.getDriverAuth()
    }

    //User가 작성한 게시글 혹은 진행중인 게시글 조회
    public fun getUserPostData(){
        repository!!.getUserPostData(userPostDto)
    }

    fun getPostByDistrict(currentPostType: String, district: String) {
        repository?.getPostByDistrict(PostDistrictDto(postType = currentPostType, district = district), postItems)
    }// 지역명 기반 게시글 조회하기


}