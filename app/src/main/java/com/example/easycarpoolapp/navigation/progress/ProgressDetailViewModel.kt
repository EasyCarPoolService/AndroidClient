package com.example.easycarpoolapp.navigation.progress

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.navigation.NavigationRepository
import com.example.easycarpoolapp.navigation.progress.dto.PostReviewDto

class ProgressDetailViewModel : ViewModel() {

    private val repository = NavigationRepository.getInstance()
    public var completeFlag : MutableLiveData<Boolean> = MutableLiveData()
    fun progressToComplete(postId: Long?,
                           host_email : String?,
                           host_nickname : String?,
                           rate: Float,
                           editRateTitle: String,
                           editRateContent: String) {

        val dto = PostReviewDto(
            postId = postId!!,
            host_email = host_email!!,
            host_nickname = host_nickname!!,
            writer_email = LocalUserData.getEmail().toString(),
            writer_nickname = LocalUserData.getNickname().toString(),
            rate = rate,
            title = editRateTitle,
            content = editRateContent,
        )

        repository?.progressToComplete(dto, completeFlag)

    } //progressToComplete()




}