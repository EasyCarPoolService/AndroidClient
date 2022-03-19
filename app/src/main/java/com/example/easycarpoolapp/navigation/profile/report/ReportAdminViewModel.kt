package com.example.easycarpoolapp.navigation.profile.report

import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.navigation.NavigationRepository
import com.example.easycarpoolapp.navigation.profile.report.dto.ReportDto

class ReportAdminViewModel : ViewModel(){
    private val repository  = NavigationRepository.getInstance()

    public fun reportAdmin(report_title : String, report_content : String){
        repository?.reportAdmin(ReportDto(
            report_title = report_title,
            report_content = report_content,
            report_user_email = LocalUserData.getEmail()!!
        ))
    }


}