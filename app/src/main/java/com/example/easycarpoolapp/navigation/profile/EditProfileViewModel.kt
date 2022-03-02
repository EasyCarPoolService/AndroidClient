package com.example.easycarpoolapp.navigation.profile

import android.graphics.Bitmap
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.auth.AuthRepository
import com.example.easycarpoolapp.navigation.NavigationRepository

class EditProfileViewModel : ViewModel() {

    private val navigationRepository : NavigationRepository? = NavigationRepository.getInstance()


    public lateinit var profile_image : Bitmap
    public lateinit var nickname : String
    public lateinit var gender : String
    public var introduce_message : String = ""


    public fun editProfile(){
        navigationRepository?.editProfile(profile_image = profile_image,
            nickname = nickname,
            gender = gender,
            introduce_message = introduce_message
            )
    }

}