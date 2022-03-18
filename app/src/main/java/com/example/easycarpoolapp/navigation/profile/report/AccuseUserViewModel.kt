package com.example.easycarpoolapp.navigation.profile.report

import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.navigation.NavigationRepository
import com.example.easycarpoolapp.navigation.profile.report.dto.AccuseDto

class AccuseUserViewModel : ViewModel() {
    public var accuse_type : String = ""
    private val repository  = NavigationRepository.getInstance()

    public fun accuseUser(accused_nickname : String, accuse_content : String){
        repository?.accuseUser(AccuseDto(accuse_type = accuse_type,
            accused_nickname = accused_nickname,
            accuser_nickname = LocalUserData.getNickname()!!,
            accuse_content = accuse_content
            ))
    }//accuseUser()

}