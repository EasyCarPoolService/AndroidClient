package com.example.easycarpoolapp.fragment.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto

class CalendarHomeViewModel : ViewModel(){
    private val repository : CalendarRepository? = CalendarRepository.getInstance()
    public var postItems : MutableLiveData<ArrayList<ReservedPostDto>> = MutableLiveData()
    public var itemDetail : MutableLiveData<PostDto> = MutableLiveData()    //recylcerView item 클릭시 detail dilog 표기



    public fun getPostData(){
        repository?.getPostData(postItems)
    }//getPostData()


    //calendar 의 recylcerview 클릭시 해당 아이템의 디테일 정보 서버로 요청
    public fun getItemDetail(reservedPostDto: ReservedPostDto){
        repository?.getItemDetail(reservedPostDto, itemDetail)
    }

}