package com.example.easycarpoolapp.navigation.profile

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.navigation.NavigationRepository

class EditProfileViewModel : ViewModel() {

    private val navigationRepository : NavigationRepository? = NavigationRepository.getInstance()

    public lateinit var profile_image : Bitmap
    public lateinit var nickname : String
    public lateinit var gender : String
    public var introduce_message : String = ""
    public val transaction_flag : MutableLiveData<String> = MutableLiveData()
    public fun editProfile(){
        navigationRepository?.editProfile(profile_image = profile_image,
            nickname = nickname,
            gender = gender,
            introduce_message = introduce_message,
            transaction_flag
            )

    } //editProfile()



}